package com.example.baimsdailyforecast.domain.usecases.getcities

import com.example.baimsdailyforecast.models.CitiesResponse
import com.example.baimsdailyforecast.domain.repo.cities.ICityRepo
import kotlinx.coroutines.flow.Flow

class ICitiesUseCaseImp(private val repo: ICityRepo): ICitiesUseCase
{
    override suspend fun getCities(): Flow<CitiesResponse>
    {
        return repo.getCities()
    }
}