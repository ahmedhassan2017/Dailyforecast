package com.example.baimsdailyforecast.ui.home.repo

import com.example.baimsdailyforecast.BuildConfig
import com.example.baimsdailyforecast.data.remote.ApiClient
import com.example.baimsdailyforecast.data.remote.ApiInterface
import com.example.baimsdailyforecast.data.remote.ApiResponse
import com.example.baimsdailyforecast.models.CitiesResponse
import com.example.baimsdailyforecast.models.WeatherResponse
import javax.inject.Inject

class WeatherRepo @Inject constructor(
    private val apiInterface: ApiInterface
): IWeatherRepo {
    override fun getWeather(lat: Double, lon: Double, apiKey: String): ApiResponse<WeatherResponse>
    {
        val call = apiInterface.getWeather(lat, lon, apiKey)
        return ApiResponse.forCall(call,BuildConfig.API_BASE)
    }
}