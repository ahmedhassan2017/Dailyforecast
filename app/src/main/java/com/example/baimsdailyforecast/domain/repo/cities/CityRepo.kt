package com.example.baimsdailyforecast.domain.repo.cities

import com.example.baimsdailyforecast.data.remote.ApiCityInterface
import com.example.baimsdailyforecast.models.CitiesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CityRepo @Inject constructor(private val apiCityInterface: ApiCityInterface) : ICityRepo
{
    override suspend fun getCities(): Flow<CitiesResponse>
    {
        return flow {
            emit(apiCityInterface.getCitiesData())
        }.flowOn(Dispatchers.IO)
    }
}