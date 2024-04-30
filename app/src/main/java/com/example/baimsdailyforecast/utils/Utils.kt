package com.example.baimsdailyforecast.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.baimsdailyforecast.R
import java.time.LocalDate
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

    fun background(context: Context): Drawable?
    {
        val timeOfDay = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
        {
            in 2..6 -> context.getDrawable(R.drawable.bg_night)
            in 7..16 -> context.getDrawable(R.drawable.bg_light)
            else -> context.getDrawable(R.drawable.bg_night)
        }
        return timeOfDay
    }


}