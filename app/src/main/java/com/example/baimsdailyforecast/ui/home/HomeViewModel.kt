package com.example.baimsdailyforecast.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baimsdailyforecast.data.db.AppDataBase
import com.example.baimsdailyforecast.models.WeatherDataDB
import com.example.baimsdailyforecast.models.City1
import com.example.baimsdailyforecast.models.WeatherResponse
import com.example.baimsdailyforecast.domain.usecases.getcities.ICitiesUseCase
import com.example.baimsdailyforecast.domain.usecases.getweather.IWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val weatherUseCase: IWeatherUseCase, val citiesUseCase: ICitiesUseCase) : ViewModel()
{

    private lateinit var disposable: Disposable
    val weather = MutableLiveData<WeatherResponse?>()
    val cities = MutableLiveData<List<City1>?>()
    val weatherDB = MutableLiveData<WeatherDataDB?>()
    val citiesDB = MutableLiveData<List<City1>?>()
    val weatherError = MutableLiveData<String>()
    val weatherDBError = MutableLiveData<String>()
    val citiesError = MutableLiveData<String>()

    val isLoading = MutableLiveData<Boolean>()


    fun getWeather(lat: Double, lon: Double, apiKey: String)
    {
        viewModelScope.launch(Dispatchers.IO) {


            weatherUseCase.getWeather(lat, lon, apiKey).onStart {
                isLoading.postValue(true) }
                .catch {e->
                    isLoading.postValue(false)
                    when (e) {
                        is ConnectException, is UnknownHostException -> {
                            weatherError.postValue("No internet connection")
                        }
                        else -> {
                            weatherError.postValue(e.message ?: "Unknown error occurred")
                        }
                    }

                }.collect {
                    isLoading.postValue(false)
                    weather.postValue(it)
                }
        }
    }


    fun getCities() {
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            citiesUseCase.getCities().onStart {
                isLoading.postValue(true)
            }.catch { e ->
                isLoading.postValue(false)
                when (e) {
                    is ConnectException, is UnknownHostException -> {
                        citiesError.postValue("No internet connection")
                    }
                    else -> {
                        citiesError.postValue(e.message ?: "Unknown error occurred")
                    }
                }
            }.collect { citiesResult ->
                cities.postValue(citiesResult.cities)
                isLoading.postValue(false)
            }
        }
    }

    // Using RX
    fun getDBCities(appDataBase: AppDataBase?)
    {
        appDataBase?.citiesDao()?.getCities()?.subscribeOn(Schedulers.computation())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<List<City1>>
            {
                override fun onSubscribe(d: Disposable)
                {
                    disposable = d
                }

                override fun onError(e: Throwable)
                {
                    citiesError.postValue(e.message)
                    disposable.dispose()
                }

                override fun onSuccess(t: List<City1>)
                {
                    citiesDB.postValue(t)
                    disposable.dispose()
                }


            })


    }

    fun insertDBCities(appDataBase: AppDataBase?, list: List<City1>)
    {
        appDataBase?.citiesDao()?.insertCity(list)?.subscribeOn(Schedulers.computation())?.subscribe(object : CompletableObserver
            {
                override fun onSubscribe(d: Disposable)
                {
                    disposable = d
                }

                override fun onComplete()
                {
                    Log.i("Ahmed", "onComplete: city Cashed ")
                }

                override fun onError(e: Throwable)
                {
                    citiesError.postValue(e.message)
                }


            })


    }

    fun getDBWeather(appDataBase: AppDataBase?, lat: Double, lon: Double)
    {
        appDataBase?.weatherDao()?.getWeather(lat, lon)?.subscribeOn(Schedulers.computation())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<WeatherDataDB>
            {
                override fun onSubscribe(d: Disposable)
                {
                    disposable = d
                }

                override fun onError(e: Throwable)
                {
                    weatherDBError.postValue(e.message)
                }

                override fun onSuccess(t: WeatherDataDB)
                {
                    weatherDB.postValue(t)

                }
            })
    }

    fun insertDBWeather(appDataBase: AppDataBase?, weatherDataDB: WeatherDataDB)
    {
        appDataBase?.weatherDao()?.insertWeather(weatherDataDB)?.subscribeOn(Schedulers.computation())?.subscribe(object : CompletableObserver
            {
                override fun onSubscribe(d: Disposable)
                {
                    disposable = d
                }

                override fun onComplete()
                {
                    Log.i("Ahmed", "onComplete: weather Cashed ")
                }

                override fun onError(e: Throwable)
                {
                    weatherDBError.postValue(e.message)
                }

            })
    }

}