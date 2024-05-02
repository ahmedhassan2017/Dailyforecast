package com.example.baimsdailyforecast.data.remote

import com.example.baimsdailyforecast.models.CitiesResponse
import com.example.baimsdailyforecast.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCityInterface
{

    /**
     * Get cities JSON data.
     */
    @GET("uploads/cities.json")
     suspend fun getCitiesData(): CitiesResponse


}


