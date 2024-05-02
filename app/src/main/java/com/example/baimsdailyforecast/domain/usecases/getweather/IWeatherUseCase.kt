package com.example.baimsdailyforecast.domain.usecases.getweather

import com.example.baimsdailyforecast.models.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface IWeatherUseCase
{
    suspend fun getWeather(lat:Double, lon:Double, apiKey:String): Flow<WeatherResponse>

}