package com.example.baimsdailyforecast.data.db.cities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.baimsdailyforecast.models.City1
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CitiesDao
{
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insertCity(cities: List<City1>) : Completable


    // get all data from db
    @Query("select * from cities_table")
    fun getCities(): Single<List<City1>>
}