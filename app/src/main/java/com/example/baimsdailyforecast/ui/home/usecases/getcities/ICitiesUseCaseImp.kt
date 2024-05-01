package com.example.baimsdailyforecast.ui.home.usecases.getcities

import com.example.baimsdailyforecast.data.remote.ApiResponse
import com.example.baimsdailyforecast.models.CitiesResponse
import com.example.baimsdailyforecast.ui.home.repo.ICityRepo

class ICitiesUseCaseImp(private val repo: ICityRepo):ICitiesUseCase
{
    override suspend fun getCities(): ApiResponse<CitiesResponse>
    {
        return repo.getCities()
    }
}