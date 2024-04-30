package com.example.baimsdailyforecast.models

data class CitiesResponse(
        val cities: MutableList<City1>
)

data class City1(
        val id: Int,
        val cityNameAr: String,
        val cityNameEn: String,
        val lat: Double,
        val lon: Double
)
