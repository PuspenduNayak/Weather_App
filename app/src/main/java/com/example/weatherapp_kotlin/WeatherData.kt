package com.example.weatherapp_kotlin

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>,
    val sys: Sys
)

data class ForecastResponse(
    val list: List<ForecastItem>,
    val city: City
)

data class ForecastItem(
    val dt: Long,
    @SerializedName("dt_txt")
    val dtTxt: String,
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val humidity: Int
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class Sys(
    val country: String
)

data class City(
    val name: String,
    val country: String
)

data class DailyForecast(
    val day: String,
    val date: String,
    val maxTemp: Int,
    val minTemp: Int,
    val description: String
)