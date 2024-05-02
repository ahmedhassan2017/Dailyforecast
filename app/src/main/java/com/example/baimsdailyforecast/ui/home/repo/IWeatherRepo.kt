package com.example.baimsdailyforecast.ui.home.repo

import com.example.baimsdailyforecast.data.remote.ApiResponse
import com.example.baimsdailyforecast.models.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface IWeatherRepo
{
    suspend fun getWeather(lat:Double, lon:Double, apiKey:String): Flow<WeatherResponse>
}