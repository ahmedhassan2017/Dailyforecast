package com.example.baimsdailyforecast.ui.home.usecases.getcities

import com.example.baimsdailyforecast.data.remote.ApiResponse
import com.example.baimsdailyforecast.models.CitiesResponse
import kotlinx.coroutines.flow.Flow

interface ICitiesUseCase
{
    suspend fun getCities(): Flow<CitiesResponse>
}