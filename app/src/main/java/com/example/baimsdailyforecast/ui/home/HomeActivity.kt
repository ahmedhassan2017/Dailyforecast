package com.example.baimsdailyforecast.ui.home

import android.annotation.SuppressLint
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
import com.example.baimsdailyforecast.BuildConfig
import com.example.baimsdailyforecast.databinding.ActivityHomeBinding
import com.example.baimsdailyforecast.ui.home.adapters.DaysAdapter
import com.example.baimsdailyforecast.utils.Extensions.toCelsiusString
import com.example.baimsdailyforecast.utils.Utils.getBackgroundBasedOnCairoTime
import com.example.baimsdailyforecast.utils.Utils.getFormattedDate
import com.example.baimsdailyforecast.utils.Utils.getImageCondition
import com.example.baimsdailyforecast.utils.Utils.greeting

class HomeActivity : AppCompatActivity()
{
    lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.welcomeTv.text = greeting()
        binding.background.background = getBackgroundBasedOnCairoTime(this)



        viewModel.getCities()


        viewModel.weather.observe(this) {
            Log.i("TAG", "Weather: ${it?.city?.name}")

            with(it?.list?.get(0)) {
//                binding.background.background = this?.dt_txt?.let { it1 -> getDrawableBasedOnTime(it1,this@HomeActivity) }
                binding.temp.text = this?.main?.temp?.toCelsiusString()
                binding.date.text = getFormattedDate()
                binding.condition.text = this?.weather?.get(0)?.main
                binding.conditionDescription.text = "Weather now is  ${this?.weather?.get(0)?.description} and clouds are ${this?.clouds?.all}%, also the wind speed is ${this?.wind?.speed} meter/sec, " + "in addition to the humidity today is ${this?.main?.humidity}%"
                binding.imageTmp.setImageDrawable(AppCompatResources.getDrawable(this@HomeActivity, getImageCondition(this!!)))


            }


        binding.days.adapter= it?.list?.let { it1 -> DaysAdapter(it1,this@HomeActivity) }


            binding.dataContainer.visibility = View.VISIBLE
        }




        viewModel.cities.observe(this) { cities ->
            if (!cities?.isEmpty()!!)
            {
                binding.textInputLayoutCity.apply {
                    (editText as? AutoCompleteTextView)?.apply {
                        setAdapter(ArrayAdapter(context,
                            android.R.layout.simple_spinner_dropdown_item,
                            cities.map { it.cityNameEn }))

                        setOnItemClickListener { _, _, position, _ ->
                            viewModel.getWeather(cities[position].lat, cities[position].lon, BuildConfig.API_Key)
                        }

                    }
                }
            } else
            {
                viewModel.getWeather(cities[0].lat, cities[0].lon, BuildConfig.API_Key)
            }
        }


        viewModel.error.observe(this) {
            Log.i("TAG", "error: $it")

        }

        viewModel.isLoading.observe(this) {

        }
    }


}