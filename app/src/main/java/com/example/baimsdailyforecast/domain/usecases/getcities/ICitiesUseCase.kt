package com.example.baimsdailyforecast.domain.usecases.getcities

import com.example.baimsdailyforecast.models.CitiesResponse
import kotlinx.coroutines.flow.Flow

interface ICitiesUseCase
{
    suspend fun getCities(): Flow<CitiesResponse>
}