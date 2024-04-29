package com.example.baimsdailyforecast.ui.home.repo

import com.example.baimsdailyforecast.BuildConfig
import com.example.baimsdailyforecast.data.remote.ApiClient
import com.example.baimsdailyforecast.data.remote.ApiResponse
import com.example.baimsdailyforecast.models.CitiesResponse
import com.example.baimsdailyforecast.models.WeatherResponse

object HomeRepo
{

     fun getWeather(lat:Double, lon:Double, apiKey:String): ApiResponse<WeatherResponse>
    {
        val call = ApiClient().service(BuildConfig.API_BASE).getWeather(lat, lon, apiKey)
        return ApiResponse.forCall(call,BuildConfig.API_BASE)
    }
   fun getCities(): ApiResponse<CitiesResponse>
    {

        val call = ApiClient().service(BuildConfig.API_CITY).getCitiesData()
        return ApiResponse.forCall(call,BuildConfig.API_CITY)
    }



}