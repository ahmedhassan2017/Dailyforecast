package com.example.baimsdailyforecast.data.remote

import com.example.baimsdailyforecast.models.WeatherResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface
{
    /**
     * Get weather forecast based on latitude and longitude.
     */
    @GET("forecast")
     suspend fun getWeather(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double,
            @Query("appid") apiKey: String
    ): WeatherResponse


}


