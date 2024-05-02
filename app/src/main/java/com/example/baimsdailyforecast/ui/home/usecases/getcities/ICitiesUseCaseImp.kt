package com.example.baimsdailyforecast.ui.home.usecases.getcities

import com.example.baimsdailyforecast.data.remote.ApiResponse
import com.example.baimsdailyforecast.models.CitiesResponse
import com.example.baimsdailyforecast.ui.home.repo.ICityRepo
import kotlinx.coroutines.flow.Flow

class ICitiesUseCaseImp(private val repo: ICityRepo):ICitiesUseCase
{
    override suspend fun getCities(): Flow<CitiesResponse>
    {
        return repo.getCities()
    }
}