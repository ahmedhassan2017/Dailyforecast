package com.example.baimsdailyforecast.ui.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import com.example.baimsdailyforecast.BuildConfig
import com.example.baimsdailyforecast.data.db.AppDataBase
import com.example.baimsdailyforecast.databinding.ActivityHomeBinding
import com.example.baimsdailyforecast.models.City1
import com.example.baimsdailyforecast.models.WeatherDataDB
import com.example.baimsdailyforecast.ui.home.adapters.DaysAdapter
import com.example.baimsdailyforecast.utils.Extensions.toCelsiusString
import com.example.baimsdailyforecast.utils.Utils.getBackgroundBasedOnCairoTime
import com.example.baimsdailyforecast.utils.Utils.getFormattedDate
import com.example.baimsdailyforecast.utils.Utils.getImageCondition
import com.example.baimsdailyforecast.utils.Utils.greeting
import com.example.baimsdailyforecast.utils.isNetworkAvailable

class HomeActivity : AppCompatActivity()
{
    private var cities: List<City1>? = null
    private val cashIsEmpty: Boolean = true
    var appDataBase: AppDataBase? = null
    lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    @SuppressLint("SetTextI18n") @RequiresApi(Build.VERSION_CODES.O) override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)


        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDataBase = AppDataBase.getINSTANCE(this)


        binding.welcomeTv.text = greeting()
        binding.background.background = getBackgroundBasedOnCairoTime(this)
        binding.date.text = getFormattedDate()


        viewModel.getCities()



        viewModel.weather.observe(this) {
            binding.allView.visibility = View.VISIBLE
            binding.internetWarning.visibility = View.GONE


            Log.i("TAG", "Weather: ${it?.city?.id}")

            with(it?.list?.get(0)) {
                viewModel.insertDBWeather(appDataBase,
                    WeatherDataDB(this?.main?.temp?.toCelsiusString(), this?.weather?.get(0)?.main, this?.main?.humidity, this?.clouds?.all,
                        this?.weather?.get(0)?.description, this?.wind?.speed, this?.dt_txt, this?.weather?.get(0)?.main, it?.city?.coord?.lat!!,
                        it.city.coord.lon, it.city.country.toString()))


//                binding.background.background = this?.dt_txt?.let { it1 -> getDrawableBasedOnTime(it1,this@HomeActivity) }
                binding.temp.text = this?.main?.temp?.toCelsiusString()
                binding.condition.text = this?.weather?.get(0)?.main
                binding.conditionDescription.text = "Weather now is  ${
                    this?.weather?.get(0)?.description
                } and clouds are ${this?.clouds?.all}%, also the wind speed is ${this?.wind?.speed} meter/sec, " + "in addition to the humidity today is ${this?.main?.humidity}%"
                binding.imageTmp.setImageDrawable(AppCompatResources.getDrawable(this@HomeActivity, getImageCondition(this?.weather!![0].main)))


            }


            binding.days.adapter = it?.list?.let { it1 -> DaysAdapter(it1, this@HomeActivity) }


            binding.dataContainer.visibility = View.VISIBLE
        }




        viewModel.cities.observe(this) { cities ->
            if (cities != null)
            {
                viewModel.insertDBCities(appDataBase, cities)
            }
            showCities(cities)

            binding.internetWarning.visibility = View.GONE
        }


        viewModel.error.observe(this) {
            Log.i("TAG", "error: $it")

            if (it.contains("connection"))
            {

                viewModel.getDBCities(appDataBase)

            } else
            {
                binding.allView.visibility = View.GONE
                binding.dataContainer.visibility = View.GONE
                binding.retry.visibility = View.VISIBLE
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isLoading.observe(this) {

            if (it)
            {
                binding.loadingView.visibility = View.VISIBLE
                binding.retry.visibility = View.GONE
            } else binding.loadingView.visibility = View.GONE
        }



        viewModel.citiesDB.observe(this) {
            cities = it
            Log.i("Ahmed", "Db cities: $it")
            if (it?.isEmpty() == true)
            {
                binding.allView.visibility = View.GONE
                binding.dataContainer.visibility = View.GONE
                binding.retry.visibility = View.VISIBLE
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
                return@observe
            }

            showCities(it)


        }



        viewModel.weatherDB.observe(this) {
            if (it == null)
            {
                binding.allView.visibility = View.GONE
                binding.dataContainer.visibility = View.GONE
                binding.retry.visibility = View.VISIBLE
                return@observe
            }


            binding.temp.text = it.temp
            binding.date.text = getFormattedDate()
            binding.condition.text = it.main
            binding.conditionDescription.text = "Weather now is  ${
                it.description
            } and clouds are ${it.cloudsAll}%, also the wind speed is ${it.windSpeed} meter/sec, " + "in addition to the humidity today is ${it.humidity}%"
            binding.imageTmp.setImageDrawable(AppCompatResources.getDrawable(this@HomeActivity, getImageCondition(it.weatherMain!!)))
            binding.dataContainer.visibility = View.VISIBLE
            binding.internetWarning.visibility = View.VISIBLE
        }



        binding.retry.setOnClickListener {
            viewModel.getCities()
        }
    }


    private fun showCities(cities: List<City1>?)
    {
        binding.allView.visibility = View.VISIBLE
        if (!cities?.isEmpty()!!)
        {
            binding.textInputLayoutCity.apply {
                (editText as? AutoCompleteTextView)?.apply {
                    setAdapter(ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, cities.map { it.cityNameEn }))


                    setOnItemClickListener { _, _, position, _ ->
                        Log.i("TAG", "Weather click: ${cities[position].id}")
                        if (isNetworkAvailable(this@HomeActivity)) viewModel.getWeather(cities[position].lat, cities[position].lon,
                            BuildConfig.API_Key)
                        else viewModel.getDBWeather(appDataBase, cities[position].lat, cities[position].lon)
                    }

                }
            }
        } else
        {
            viewModel.getWeather(cities[0].lat, cities[0].lon, BuildConfig.API_Key)
        }
    }


}