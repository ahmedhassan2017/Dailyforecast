package com.example.baimsdailyforecast.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baimsdailyforecast.data.remote.ApiResponse
import com.example.baimsdailyforecast.models.CitiesResponse
import com.example.baimsdailyforecast.models.WeatherResponse
import com.example.baimsdailyforecast.ui.home.repo.HomeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class HomeViewModel :ViewModel()
{

    val weather = MutableLiveData<WeatherResponse?>()
    val cities = MutableLiveData<CitiesResponse?>()
    val error = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()



    fun getWeather(lat:Double, lon:Double, apiKey:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = HomeRepo.getWeather(lat, lon, apiKey)
            if (!isActive) return@launch
            when{
                res.isSuccess()->{
                    weather.postValue(res.data)
                    isLoading.postValue(false)
                }

                !res.isSuccess() ->
                {
                    error.postValue(res.error?.message)
                    isLoading.postValue(false)
                }
            }
        }
    }

    fun getCities() {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val res = HomeRepo.getCities()
            if (!isActive) return@launch

            when{
                res.isSuccess()->{
                    cities.postValue(res.data)
                    isLoading.postValue(false)
                }

                !res.isSuccess() ->
                {
                    error.postValue(res.error?.message)
                    isLoading.postValue(false)
                }
            }







        }
    }
}