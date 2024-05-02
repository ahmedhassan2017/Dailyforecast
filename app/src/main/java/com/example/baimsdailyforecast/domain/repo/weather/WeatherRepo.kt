package com.example.baimsdailyforecast.domain.repo.weather

import com.example.baimsdailyforecast.data.remote.ApiInterface
import com.example.baimsdailyforecast.models.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepo @Inject constructor(private val apiInterface: ApiInterface): IWeatherRepo
{

    override suspend fun getWeather(lat: Double, lon: Double, apiKey: String): Flow<WeatherResponse>
    {
        return flow {
            emit(apiInterface.getWeather(lat, lon, apiKey))
        }.flowOn(Dispatchers.IO)
    }
}