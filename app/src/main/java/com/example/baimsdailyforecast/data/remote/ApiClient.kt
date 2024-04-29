package com.example.baimsdailyforecast.data.remote

import com.example.baimsdailyforecast.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    private lateinit var mRetrofit: Retrofit
    private lateinit var mService: ApiInterface


    /**
     * Request interceptors to add headers and optionally monitor requests.
     */
    private fun interceptors(): Array<Interceptor> {


        return arrayOf(
            Interceptor { chain ->
                chain.run {
                    proceed(request().newBuilder().apply {

                        addHeader("Accept", "application/json")
                        addHeader("User-Agent", "Android/${BuildConfig.VERSION_CODE}")
                    }.build())
                }
            },
        )
    }

    /**
     * OkHttp client to be used in Retrofit and Picasso.
     *
     * This is where we setup caching and add interceptors.
     */
    fun client(): OkHttpClient
    {
        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        val interceptors = interceptors()

        for (interceptor in interceptors) {
            builder.addInterceptor(interceptor)
        }
        /** logging interceptor to log network requests to logCat   */
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    /**
     * Retrofit instance creator method.
     *
     * This is where we setup JSON decoding and the API's base URL.
     */
    fun retrofit(baseUrl:String): Retrofit
    {
        if (!::mRetrofit.isInitialized) {
            val client = client()
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

            mRetrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(baseUrl).build()
        }

        return mRetrofit
    }



    /**
     * Retrofit service created from our API interface.
     */
    fun service(baseUrl:String): ApiInterface {
        if (!::mService.isInitialized) {
            mService = retrofit(baseUrl).create(ApiInterface::class.java)
        }

        return mService
    }


}