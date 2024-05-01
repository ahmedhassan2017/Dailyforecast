package com.example.baimsdailyforecast.data.remote

import com.example.baimsdailyforecast.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface
{
    /**
     * Get weather forecast based on latitude and longitude.
     */
    @GET("forecast")
     fun getWeather(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double,
            @Query("appid") apiKey: String
    ): Call<WeatherResponse>


}


