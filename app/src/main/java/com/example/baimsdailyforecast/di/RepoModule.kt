package com.example.baimsdailyforecast.di

import com.example.baimsdailyforecast.data.remote.ApiCityInterface
import com.example.baimsdailyforecast.data.remote.ApiInterface
import com.example.baimsdailyforecast.ui.home.repo.CityRepo
import com.example.baimsdailyforecast.ui.home.repo.ICityRepo
import com.example.baimsdailyforecast.ui.home.repo.IWeatherRepo
import com.example.baimsdailyforecast.ui.home.repo.WeatherRepo
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