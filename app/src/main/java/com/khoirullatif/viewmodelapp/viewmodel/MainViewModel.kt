package com.khoirullatif.viewmodelapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khoirullatif.viewmodelapp.model.WeatherItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.DecimalFormat

class MainViewModel : ViewModel() {
    val listWeathers = MutableLiveData<ArrayList<WeatherItems>>()

    fun setWeather(city: String) {
        //request API
        val listItem = ArrayList<WeatherItems>()

        val apiKey = "b3a57c158f52a9d6d931b418f1ddbbf0"
        val url =  "https://api.openweathermap.org/data/2.5/group?id=${city}&units=metric&appid=${apiKey}"

        val client = AsyncHttpClient()
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                //parsing data
                val result = String(responseBody!!)
                Log.d("onSuccess: ", result)
                try {
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("list")

                    for (i in 0 until list.length()) {
                        val itemList = list.getJSONObject(i)
                        val weatherItems = WeatherItems()
                        weatherItems.id = itemList.getInt("id")
                        weatherItems.name = itemList.getString("name")
                        weatherItems.currentWeather = itemList.getJSONArray("weather").getJSONObject(0).getString("main")
                        weatherItems.description = itemList.getJSONArray("weather").getJSONObject(0).getString("description")
                        val temp = itemList.getJSONObject("main").getInt("temp")
                        weatherItems.temperature = DecimalFormat("##.##").format(temp)

                        listItem.add(weatherItems)
                    }

                    //post value ke mutablelivedata
                    listWeathers.postValue(listItem)

                } catch (e: Exception) {
                    Log.d( "exception ", e.message.toString())
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d( "onFailure:", error?.message.toString())
            }

        })
    }

    fun getWeathers(): LiveData<ArrayList<WeatherItems>> {
        return listWeathers
    }
}