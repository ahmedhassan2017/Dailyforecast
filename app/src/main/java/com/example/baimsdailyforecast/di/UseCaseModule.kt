package com.example.baimsdailyforecast.di

import com.example.baimsdailyforecast.domain.repo.cities.ICityRepo
import com.example.baimsdailyforecast.domain.repo.weather.IWeatherRepo
import com.example.baimsdailyforecast.domain.usecases.getcities.ICitiesUseCase
import com.example.baimsdailyforecast.domain.usecases.getcities.ICitiesUseCaseImp
import com.example.baimsdailyforecast.domain.usecases.getweather.IWeatherUseCase
import com.example.baimsdailyforecast.domain.usecases.getweather.IWeatherUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule
{
    @Provides
    @Singleton
    fun provideWeatherUseCase(repo: IWeatherRepo): IWeatherUseCase
    {
        return IWeatherUseCaseImp(repo)
    }


    @Provides
    @Singleton
    fun provideCitiesUseCase(repo: ICityRepo): ICitiesUseCase
    {
        return ICitiesUseCaseImp(repo)
    }
}

