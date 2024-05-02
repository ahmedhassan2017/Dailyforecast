package com.example.baimsdailyforecast.di

import com.example.baimsdailyforecast.data.remote.ApiCityInterface
import com.example.baimsdailyforecast.data.remote.ApiInterface
import com.example.baimsdailyforecast.domain.repo.cities.CityRepo
import com.example.baimsdailyforecast.domain.repo.cities.ICityRepo
import com.example.baimsdailyforecast.domain.repo.weather.IWeatherRepo
import com.example.baimsdailyforecast.domain.repo.weather.WeatherRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule
{
    @Provides
    @Singleton
    fun provideWeatherRepo(apiInterface: ApiInterface): IWeatherRepo
    {
        return WeatherRepo(apiInterface)
    }

    @Provides
    @Singleton
    fun provideCityRepo(apiCityInterface: ApiCityInterface): ICityRepo
    {
        return CityRepo(apiCityInterface)
    }
}