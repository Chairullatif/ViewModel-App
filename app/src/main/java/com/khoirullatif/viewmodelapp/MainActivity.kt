package com.khoirullatif.viewmodelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.khoirullatif.viewmodelapp.adapter.WeatherAdapter
import com.khoirullatif.viewmodelapp.databinding.ActivityMainBinding
import com.khoirullatif.viewmodelapp.model.WeatherItems
import com.khoirullatif.viewmodelapp.viewmodel.MainViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.DecimalFormat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var adapter: WeatherAdapter
    private lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        adapter = WeatherAdapter()
        adapter.notifyDataSetChanged()

        binding.rvCity.layoutManager = LinearLayoutManager(this)
        binding.rvCity.adapter = adapter

        binding.btnCity.setOnClickListener(this)

        // 1. cara 1
//        mainViewModel.getWeathers().observe(this, { weatherItem ->
//            if (weatherItem != null) {
//                adapter.setData(weatherItem)
//                showLoading(false)
//            }
//        })

        //2. cara 2
        mainViewModel.getWeathers().observe(this, {
            if (it != null) {
                adapter.setData(it)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

// Without ViewModel
//    private fun setWeatherData(city: String) {
//        var listItem = ArrayList<WeatherItems>()
//
//        val apiKey = "b3a57c158f52a9d6d931b418f1ddbbf0"
//        val url =  "https://api.openweathermap.org/data/2.5/group?id=${city}&units=metric&appid=${apiKey}"
//
//        val client = AsyncHttpClient()
//        client.get(url, object : AsyncHttpResponseHandler(){
//            override fun onSuccess(
//                statusCode: Int,
//                headers: Array<out Header>?,
//                responseBody: ByteArray?
//            ) {
//                val result = String(responseBody!!)
//                Log.d(TAG, "onSuccess: $result")
//                try {
//                    val responseObject = JSONObject(result)
//                    val list = responseObject.getJSONArray("list")
//
//                    for (i in 0 until list.length()) {
//                        val itemList = list.getJSONObject(i)
//                        val weatherItems = WeatherItems()
//                        weatherItems.id = itemList.getInt("id")
//                        weatherItems.name = itemList.getString("name")
//                        weatherItems.currentWeather = itemList.getJSONArray("weather").getJSONObject(0).getString("main")
//                        weatherItems.description = itemList.getJSONArray("weather").getJSONObject(0).getString("description")
//                        val temp = itemList.getJSONObject("main").getInt("temp")
//                        weatherItems.temperature = DecimalFormat("##.##").format(temp)
//
//                        listItem.add(weatherItems)
//                    }
//
//                    adapter.setData(listItem)
//                    showLoading(false)
//                } catch (e: Exception) {
//                    Log.d(TAG, "exception ${e.message.toString()}")
//                    e.printStackTrace()
//                }
//            }
//
//            override fun onFailure(
//                statusCode: Int,
//                headers: Array<out Header>?,
//                responseBody: ByteArray?,
//                error: Throwable?
//            ) {
//                Log.d(TAG, "onFailure: ${error?.message.toString()}")
//            }
//
//        })
//    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.btn_city) {
            val city = binding.edtCity.text.toString()
            if (city.isEmpty()) return
            showLoading(true)

//            dengan ViewModel
            mainViewModel.setWeather(city)

//          without ViewModel
//            setWeatherData(city)
        }
    }
}