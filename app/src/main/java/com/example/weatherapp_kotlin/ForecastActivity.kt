package com.example.weatherapp_kotlin

import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ForecastActivity : AppCompatActivity() {

    private lateinit var weatherApiService: WeatherApiService
    private lateinit var rvForecast: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var ivBack: ImageView
    private lateinit var forecastAdapter: WeatherAdapter

    private val apiKey = "cbc9015126156ed02d28ca43bda76e36" // Replace with your OpenWeatherMap API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        initViews()
        setupRecyclerView()
        setupClickListeners()

        val lat = intent.getDoubleExtra("lat", 0.0)
        val lon = intent.getDoubleExtra("lon", 0.0)
        val city = intent.getStringExtra("city") ?: ""

        if (lat != 0.0 && lon != 0.0) {
            fetchForecastData(lat, lon)
        } else if (city.isNotEmpty()) {
            fetchForecastDataByCity(city)
        }
    }

    private fun initViews() {
        rvForecast = findViewById(R.id.rvForecast)
        progressBar = findViewById(R.id.progressBar)
        ivBack = findViewById(R.id.ivBack)
        weatherApiService = WeatherApiService.create()
    }

    private fun setupRecyclerView() {
        forecastAdapter = WeatherAdapter()
        rvForecast.layoutManager = LinearLayoutManager(this)
        rvForecast.adapter = forecastAdapter
    }

    private fun setupClickListeners() {
        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun fetchForecastData(lat: Double, lon: Double) {
        showLoading(true)
        lifecycleScope.launch {
            try {
                val response = weatherApiService.getForecast(lat, lon, apiKey)
                if (response.isSuccessful) {
                    response.body()?.let { forecast ->
                        val dailyForecasts = processForecastData(forecast.list)
                        forecastAdapter.updateData(dailyForecasts)
                    }
                } else {
                    showError("Failed to fetch forecast data")
                }
            } catch (e: Exception) {
                showError("Error: ${e.message}")
            } finally {
                showLoading(false)
            }
        }
    }

    private fun fetchForecastDataByCity(city: String) {
        showLoading(true)
        lifecycleScope.launch {
            try {
                val response = weatherApiService.getForecastByCity(city, apiKey)
                if (response.isSuccessful) {
                    response.body()?.let { forecast ->
                        val dailyForecasts = processForecastData(forecast.list)
                        forecastAdapter.updateData(dailyForecasts)
                    }
                } else {
                    showError("Failed to fetch forecast data")
                }
            } catch (e: Exception) {
                showError("Error: ${e.message}")
            } finally {
                showLoading(false)
            }
        }
    }

    private fun processForecastData(forecastList: List<ForecastItem>): List<DailyForecast> {
        val dailyForecasts = mutableListOf<DailyForecast>()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val displayDateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())

        val groupedByDate = forecastList.groupBy {
            dateFormat.format(Date(it.dt * 1000))
        }

        groupedByDate.entries.take(5).forEach { (dateStr, items) ->
            val date = dateFormat.parse(dateStr) ?: Date()
            val dayName = dayFormat.format(date)
            val displayDate = displayDateFormat.format(date)

            val maxTemp = items.maxOf { it.main.tempMax }.toInt()
            val minTemp = items.minOf { it.main.tempMin }.toInt()
            val description = items[0].weather[0].description.capitalize()

            dailyForecasts.add(
                DailyForecast(
                    day = dayName,
                    date = displayDate,
                    maxTemp = maxTemp,
                    minTemp = minTemp,
                    description = description
                )
            )
        }

        return dailyForecasts
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}