package com.example.baimsdailyforecast.data.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.baimsdailyforecast.models.WeatherDataDB
import com.example.baimsdailyforecast.data.db.cities.CitiesDao
import com.example.baimsdailyforecast.data.db.weather.WeatherDao
import com.example.baimsdailyforecast.models.City1


@Database(entities = [ City1::class, WeatherDataDB::class], version = 3)
abstract class AppDataBase : RoomDatabase() {

    abstract fun citiesDao(): CitiesDao?
    abstract fun weatherDao(): WeatherDao?

    companion object {
        private var INSTANCE: AppDataBase? = null

        @Synchronized
        fun getINSTANCE(context: Context): AppDataBase?
        {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java, "forecast_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE
        }
    }
}