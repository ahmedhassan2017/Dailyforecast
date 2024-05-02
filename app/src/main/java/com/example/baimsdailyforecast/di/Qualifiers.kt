package com.example.baimsdailyforecast.di

import javax.inject.Qualifier

// to determine which baseurl will be used

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl2
