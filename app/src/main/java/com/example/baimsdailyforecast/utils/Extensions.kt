package com.example.baimsdailyforecast.utils

object Extensions
{
    fun Double.toCelsiusString(): String {
        val celsius = this - 273.15
        return "${celsius.toInt()}°C"
    }

}