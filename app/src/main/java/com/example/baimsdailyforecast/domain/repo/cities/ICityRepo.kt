package com.example.baimsdailyforecast.domain.repo.cities

import com.example.baimsdailyforecast.models.CitiesResponse
import kotlinx.coroutines.flow.Flow

interface ICityRepo
{
    suspend fun getCities(): Flow<CitiesResponse>
}