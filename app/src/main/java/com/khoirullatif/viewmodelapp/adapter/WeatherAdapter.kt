package com.khoirullatif.viewmodelapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.khoirullatif.viewmodelapp.R
import com.khoirullatif.viewmodelapp.databinding.WeatherItemBinding
import com.khoirullatif.viewmodelapp.model.WeatherItems

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>(){

    private val listData = ArrayList<WeatherItems>()

    fun setData(data: ArrayList<WeatherItems>) {
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = WeatherItemBinding.bind(itemView)
        fun bind(data: WeatherItems) {
            binding.tvCityItem.text = data.name
            binding.tvDescItem.text = data.description
            binding.tvTempItem.text = data.temperature
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherAdapter.WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherAdapter.WeatherViewHolder, position: Int) {
        val itemData = listData[position]
        holder.bind(itemData)
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}