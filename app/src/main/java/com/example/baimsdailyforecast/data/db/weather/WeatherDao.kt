package com.example.baimsdailyforecast.data.db.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.baimsdailyforecast.models.WeatherDataDB
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface WeatherDao
{
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insertWeather(weatherData: WeatherDataDB) : Completable


    // get all data from db
    @Query("select * from weather_table where lat = :lat AND lon = :lon")
    fun getWeather( lat:Double, lon:Double): Single<WeatherDataDB>
}