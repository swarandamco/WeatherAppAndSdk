package com.weatherlibrary.sdk.data

import com.weatherlibrary.sdk.data.model.weather.currentmodels.WeatherResponse
import com.weatherlibrary.sdk.data.model.weather.weekmodels.WeatherWeekResponse
import io.reactivex.Single

internal interface DataSource {

    fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        tempUnit: String,
        apiKey: String
    ): Single<WeatherResponse>

    fun getCurrentWeatherforWeek(
        latitude: Double,
        longitude: Double,
        tempUnit: String,
        exclude: String,
        cnt: Int,
        apiKey: String
    ): Single<WeatherWeekResponse>

}