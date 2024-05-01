package com.example.baimsdailyforecast.ui.home.repo

import com.example.baimsdailyforecast.data.remote.ApiResponse
import com.example.baimsdailyforecast.models.WeatherResponse

interface IWeatherRepo
{
    fun getWeather(lat:Double, lon:Double, apiKey:String): ApiResponse<WeatherResponse>
}