package com.example.weatherapp_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private var forecasts = listOf<DailyForecast>()

    fun updateData(newForecasts: List<DailyForecast>) {
        forecasts = newForecasts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forecast, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(forecasts[position])
    }

    override fun getItemCount(): Int = forecasts.size

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDay: TextView = itemView.findViewById(R.id.tvDay)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val tvMaxTemp: TextView = itemView.findViewById(R.id.tvMaxTemp)
        private val tvMinTemp: TextView = itemView.findViewById(R.id.tvMinTemp)

        fun bind(forecast: DailyForecast) {
            tvDay.text = forecast.day
            tvDate.text = forecast.date
            tvDescription.text = forecast.description
            tvMaxTemp.text = "${forecast.maxTemp}°"
            tvMinTemp.text = "${forecast.minTemp}°"
        }
    }
}