package com.example.weatherapp_kotlin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var weatherApiService: WeatherApiService

    private val apiKey = "YOUR-API-KEY" // Replace with your OpenWeatherMap API key

    private lateinit var tvCityName: TextView
    private lateinit var tvTemperature: TextView
    private lateinit var tvWeatherDescription: TextView
    private lateinit var tvMinMaxTemp: TextView
    private lateinit var tvAqi: TextView
    private lateinit var btnForecast: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var ivSearch: ImageView

    private var currentLat: Double = 0.0
    private var currentLon: Double = 0.0
    private var currentCity: String = ""

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }


    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initServices()
        setupClickListeners()

        if (checkLocationPermission()) {
            getCurrentLocation()
        } else {
            requestLocationPermission()
        }
    }

    private fun initViews() {
        tvCityName = findViewById(R.id.tvCityName)
        tvTemperature = findViewById(R.id.tvTemperature)
        tvWeatherDescription = findViewById(R.id.tvWeatherDescription)
        tvMinMaxTemp = findViewById(R.id.tvMinMaxTemp)
        tvAqi = findViewById(R.id.tvAqi)
        btnForecast = findViewById(R.id.btnForecast)
        progressBar = findViewById(R.id.progressBar)
        ivSearch = findViewById(R.id.ivSearch)
    }

    private fun initServices() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        weatherApiService = WeatherApiService.create()
    }

    private fun setupClickListeners() {
        btnForecast.setOnClickListener {
            val intent = Intent(this, ForecastActivity::class.java)
            intent.putExtra("lat", currentLat)
            intent.putExtra("lon", currentLon)
            intent.putExtra("city", currentCity)
            startActivity(intent)
        }

        ivSearch.setOnClickListener {
            showSearchDialog()
        }
    }

    private fun showSearchDialog() {
        val editText = EditText(this)
        editText.hint = "Enter city name"

        AlertDialog.Builder(this)
            .setTitle("Search Location")
            .setView(editText)
            .setPositiveButton("Search") { _, _ ->
                val cityName = editText.text.toString().trim()
                if (cityName.isNotEmpty()) {
                    searchWeatherByCity(cityName)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun searchWeatherByCity(cityName: String) {
        showLoading(true)
        lifecycleScope.launch {
            try {
                val response = weatherApiService.getCurrentWeatherByCity(cityName, apiKey)
                if (response.isSuccessful) {
                    response.body()?.let { weather ->
                        currentCity = weather.name
                        updateWeatherUI(weather)
                    }
                } else {
                    showError("City not found")
                }
            } catch (e: Exception) {
                showError("Error: ${e.message}")
            } finally {
                showLoading(false)
            }
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun getCurrentLocation() {
        if (!checkLocationPermission()) return

        showLoading(true)
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                currentLat = it.latitude
                currentLon = it.longitude
                fetchWeatherData(it.latitude, it.longitude)
            } ?: run {
                showError("Unable to get location")
                showLoading(false)
            }
        }.addOnFailureListener {
            showError("Error getting location: ${it.message}")
            showLoading(false)
        }
    }

    private fun fetchWeatherData(lat: Double, lon: Double) {
        lifecycleScope.launch {
            try {
                val response = weatherApiService.getCurrentWeather(lat, lon, apiKey)
                if (response.isSuccessful) {
                    response.body()?.let { weather ->
                        currentCity = weather.name
                        updateWeatherUI(weather)
                    }
                } else {
                    showError("Failed to fetch weather data")
                }
            } catch (e: Exception) {
                showError("Error: ${e.message}")
            } finally {
                showLoading(false)
            }
        }
    }

    private fun updateWeatherUI(weather: CurrentWeatherResponse) {
        tvCityName.text = weather.name
        tvTemperature.text = "${weather.main.temp.roundToInt()}°C"
        tvWeatherDescription.text = weather.weather[0].description.capitalize()
        tvMinMaxTemp.text = "${weather.main.tempMax.roundToInt()}° / ${weather.main.tempMin.roundToInt()}°"

        // Simulate AQI (You can integrate with a real AQI API)
        val aqi = (50..150).random()
        tvAqi.text = "AQI $aqi"
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) android.view.View.VISIBLE else android.view.View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                showError("Location permission denied")
            }
        }
    }
}
