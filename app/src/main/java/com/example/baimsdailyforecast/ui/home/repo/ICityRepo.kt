package com.example.baimsdailyforecast.ui.home.repo

import com.example.baimsdailyforecast.models.CitiesResponse
import kotlinx.coroutines.flow.Flow

interface ICityRepo
{
    suspend fun getCities(): Flow<CitiesResponse>
}