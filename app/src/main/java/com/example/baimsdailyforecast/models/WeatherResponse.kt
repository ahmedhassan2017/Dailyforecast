package com.example.baimsdailyforecast.models

data class WeatherResponse(
        val cod: String?,
        val message: Int?,
        val cnt: Int?,
        val list: List<WeatherData>?,
        val city: City?
)

data class WeatherData(
        val dt: Long,
        val main: MainWeatherInfo,
        val weather: List<WeatherDescription>,
        val clouds: Clouds,
        val wind: Wind,

        val dt_txt: String
)

data class MainWeatherInfo(
        val temp: Double,
        val humidity: Int,
)

data class WeatherDescription(
        val id: Int,
        val main: String,
        val description: String,
)

data class Clouds(
        val all: Int
)

data class Wind(
        val speed: Double,
)

data class City(
        val id: Int,
        val name: String,
        val coord: Coord,
        val country: String,

)

data class Coord(
        val lat: Double,
        val lon: Double
)
