package com.example.baimsdailyforecast.ui.home

import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doAfterTextChanged
import com.example.baimsdailyforecast.BuildConfig
import com.example.baimsdailyforecast.R
import com.example.baimsdailyforecast.databinding.ActivityHomeBinding
import com.example.baimsdailyforecast.utils.Extensions.toCelsiusString
import com.example.baimsdailyforecast.utils.Utils.background
import com.example.baimsdailyforecast.utils.Utils.getFormattedDate
import com.example.baimsdailyforecast.utils.Utils.greeting
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar
import java.util.Locale
import kotlin.math.log

class HomeActivity : AppCompatActivity()
{
    lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O) override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.welcomeTv.text = greeting()
        binding.background.background = background(this)



        viewModel.getCities()


        viewModel.weather.observe(this) {
            Log.i("TAG", "Weather: ${it?.city?.name}")


            binding.temp.text = it?.list?.get(0)?.main?.temp?.toCelsiusString()
            binding.date.text = getFormattedDate()
            binding.condition.text = it?.list?.get(0)?.weather?.get(0)?.main
            binding.conditionDescription.text = "Weather now is  ${
                it?.list?.get(0)?.weather?.get(0)?.description
            } " + "and clouds are ${it?.list?.get(0)?.clouds?.all}%, " + "also the wind speed is ${
                it?.list?.get(0)?.wind?.speed
            } meter/sec, " + "in addition to the humidity today is ${it?.list?.get(0)?.main?.humidity}%"

            it?.list?.get(0)?.weather?.get(0)?.main?.let { weatherMain ->
                val drawableResId = when {
                    weatherMain.contains("Rain", ignoreCase = true) -> R.drawable.ic_cloud_rain
                    weatherMain.contains("Cloud", ignoreCase = true) -> R.drawable.ic_cloud
                    weatherMain.contains("Sun", ignoreCase = true) -> R.drawable.ic_sun
                    weatherMain.contains("Clear", ignoreCase = true) -> R.drawable.ic_moon
                    else -> R.drawable.ic_moon // Default drawable
                }

                binding.imageTmp.setImageDrawable(AppCompatResources.getDrawable(this, drawableResId))
            }


            binding.dataContainer.visibility = View.VISIBLE
        }




        viewModel.cities.observe(this) { cities ->
            if (!cities?.isEmpty()!!)
            {
                binding.textInputLayoutCity.apply {
                    (editText as? AutoCompleteTextView)?.apply {
                        setAdapter(ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, cities.map { it.cityNameEn }))

                        setOnItemClickListener { _, _, position, _ ->
                            viewModel.getWeather(cities[position].lat, cities[position].lon, BuildConfig.API_Key)
                        }

                    }
                }
            }
            else {
                viewModel.getWeather(cities[0].lat, cities[0].lon, BuildConfig.API_Key)
            }
        }


        viewModel.error.observe(this) {
            Log.i("TAG", "error: $it")

        }
    }


}