package com.example.baimsdailyforecast.ui.home.usecases.getweather

import com.example.baimsdailyforecast.data.remote.ApiResponse
import com.example.baimsdailyforecast.models.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface IWeatherUseCase
{
    suspend fun getWeather(lat:Double, lon:Double, apiKey:String): Flow<WeatherResponse>

}