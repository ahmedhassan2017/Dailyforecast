package com.example.baimsdailyforecast.domain.usecases.getweather

import com.example.baimsdailyforecast.models.WeatherResponse
import com.example.baimsdailyforecast.domain.repo.weather.IWeatherRepo
import kotlinx.coroutines.flow.Flow

class IWeatherUseCaseImp(private  val repo: IWeatherRepo): IWeatherUseCase
{
    override suspend fun getWeather(lat: Double, lon: Double, apiKey: String): Flow<WeatherResponse>
    {
        return repo.getWeather(lat, lon, apiKey)
    }
}