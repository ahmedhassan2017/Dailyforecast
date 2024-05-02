package com.example.baimsdailyforecast.di


import com.example.baimsdailyforecast.BuildConfig
import com.example.baimsdailyforecast.data.remote.ApiCityInterface
import com.example.baimsdailyforecast.data.remote.ApiInterface
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Request interceptors to add headers and optionally monitor requests.
     */
    @Provides
    @Singleton
     fun provideInterceptors(): Array<Interceptor>
    {
        return arrayOf(
            Interceptor { chain ->
                chain.run {
                    proceed(request().newBuilder().apply {

                        addHeader("Accept", "application/json")

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
    @Provides
    @Singleton
     fun provideClient(interceptors : Array<Interceptor>): OkHttpClient
    {
        val builder = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)

        for (interceptor in interceptors)
        {
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
    @Provides
    @Singleton
    @BaseUrl1
    fun provideRetrofit(client: OkHttpClient): Retrofit
    {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        return Retrofit.Builder().client(client).addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.API_BASE).build()
    }

    @Provides
    @Singleton
    @BaseUrl2
    fun provideRetrofit2(client: OkHttpClient): Retrofit
    {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        return Retrofit.Builder().client(client).addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.API_CITY).build()
    }

    /**
     * Retrofit service created from our API interface.
     */
    @Provides
    @Singleton
    fun provideApiInterface(@BaseUrl1 retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }


    @Provides
    @Singleton
    fun provideAnotherApiInterface(@BaseUrl2 retrofit: Retrofit): ApiCityInterface {
        return retrofit.create(ApiCityInterface::class.java)
    }
}