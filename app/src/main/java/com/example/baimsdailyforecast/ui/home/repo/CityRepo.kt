package com.example.baimsdailyforecast.ui.home.repo

import com.example.baimsdailyforecast.BuildConfig
import com.example.baimsdailyforecast.data.remote.ApiCityInterface
import com.example.baimsdailyforecast.data.remote.ApiClient
import com.example.baimsdailyforecast.data.remote.ApiInterface
import com.example.baimsdailyforecast.data.remote.ApiResponse
import com.example.baimsdailyforecast.models.CitiesResponse
import javax.inject.Inject

class CityRepo @Inject constructor(
        private val apiCityInterface: ApiCityInterface
) : ICityRepo
{
    override fun getCities(): ApiResponse<CitiesResponse>
    {
        val call = apiCityInterface.getCitiesData()
        return ApiResponse.forCall(call, BuildConfig.API_CITY)
    }
}