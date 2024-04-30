package com.example.baimsdailyforecast.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.baimsdailyforecast.R
import com.example.baimsdailyforecast.models.WeatherData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

object Utils

{
    @RequiresApi(Build.VERSION_CODES.O) fun getFormattedDate(): String {
        // Get current date
        val currentDate = LocalDate.now()

        // Format date as "Monday - 18 March"
        val formatter = DateTimeFormatter.ofPattern("EEEE - d MMMM", Locale.ENGLISH)
        val formattedDate = currentDate.format(formatter)

        return formattedDate
    }

    fun greeting(): String
    {
        val timeOfDay = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
        {
            in 2..11 -> "Morning"
            in 12..17 -> "Afternoon"
            else -> "Evening"
        }
        return "Good $timeOfDay"
    }

    fun getBackgroundBasedOnCairoTime(context: Context): Drawable?
    {
        val timeOfDay = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
        {
            in 2..6 -> context.getDrawable(R.drawable.bg_night)
            in 7..16 -> context.getDrawable(R.drawable.bg_light)
            else -> context.getDrawable(R.drawable.bg_night)
        }
        return timeOfDay
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDrawableBasedOnTime(dateTimeString: String, context: Context): Drawable? {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateTime = LocalDateTime.parse(dateTimeString, formatter)
        val hourOfDay = dateTime.hour

        return when (hourOfDay) {
            in 2..6 -> context.getDrawable(R.drawable.bg_night)
            in 7..16 -> context.getDrawable(R.drawable.bg_light)
            else -> context.getDrawable(R.drawable.bg_night)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayNameFromDate(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val dateTime = LocalDateTime.parse(dateString, formatter)
        val dayOfWeek = dateTime.dayOfWeek
        return dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH)
    }

    fun getImageCondition(weather: WeatherData):Int{
        weather.weather[0].main.let { weatherMain ->
            val drawableResId = when {
                weatherMain.contains("Rain", ignoreCase = true) -> R.drawable.ic_cloud_rain
                weatherMain.contains("Cloud", ignoreCase = true) -> R.drawable.ic_cloud
                weatherMain.contains("Sun", ignoreCase = true) -> R.drawable.ic_sun
                weatherMain.contains("Clear", ignoreCase = true) -> R.drawable.ic_moon
                else -> R.drawable.ic_moon // Default drawable
            }
            return drawableResId
        }


    }

}