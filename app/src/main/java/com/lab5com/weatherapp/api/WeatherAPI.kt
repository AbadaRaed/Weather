package com.lab5com.weatherapp.api

import com.lab5com.weatherapp.data.remote.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherAPI {
    @GET("onecall?")
    suspend fun getWeather(
        @Query("lat") latitude: Double?,
        @Query("lon") longitude: Double?,
        @Query("appid") apiKey: String,
        @Query("exclude") exclude: String = "minutely,daily,hourly,alerts",
        @Query("units") units: String ="metric",
        @Query("lang") lang: String ="en"
    ): WeatherResponse

}