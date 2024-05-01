package com.example.baimsdailyforecast.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CitiesResponse(
        val cities: MutableList<City1>
)
@Entity(tableName = "cities_table")
data class City1(
        @PrimaryKey
        val id: Int,
        val cityNameAr: String,
        val cityNameEn: String,
        val lat: Double,
        val lon: Double
)
