package com.example.baimsdailyforecast.domain.repo.weather

import com.example.baimsdailyforecast.models.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface IWeatherRepo
{
    suspend fun getWeather(lat:Double, lon:Double, apiKey:String): Flow<WeatherResponse>
}