package com.example.baimsdailyforecast.ui.home.repo

import com.example.baimsdailyforecast.data.remote.ApiResponse
import com.example.baimsdailyforecast.models.CitiesResponse

interface ICityRepo
{
    fun getCities(): ApiResponse<CitiesResponse>
}