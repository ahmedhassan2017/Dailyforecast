package com.example.baimsdailyforecast.ui.home.usecases.getweather

import com.example.baimsdailyforecast.data.remote.ApiResponse
import com.example.baimsdailyforecast.models.WeatherResponse
import com.example.baimsdailyforecast.ui.home.repo.IWeatherRepo

class IWeatherUseCaseImp(private  val repo: IWeatherRepo):IWeatherUseCase
{
    override suspend fun getWeather(lat: Double, lon: Double, apiKey: String): ApiResponse<WeatherResponse>
    {
        return repo.getWeather(lat, lon, apiKey)
    }
}