package com.example.baimsdailyforecast.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherDataDB(
        val temp: String?,
        val main: String?,
        val humidity: Int?,
        val cloudsAll: Int?,
        val description: String?,
        val windSpeed: Double?,
        val dt_txt: String?,
        val weatherMain:String?,
        @PrimaryKey val lat:Double,
         val lon:Double,
         val cityName: String,
)
