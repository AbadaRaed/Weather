package com.lab5com.weatherapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object WeatherAPIClient {
    private const val BASE_URL: String = "https://api.openweathermap.org/data/2.5/"

    //Add Interceptor
    var interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    //Add client
    val client = interceptor.let { OkHttpClient.Builder().addInterceptor(it).build() }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val apiInterface: WeatherAPI by lazy {
        retrofit.create(WeatherAPI::class.java)
    }
}