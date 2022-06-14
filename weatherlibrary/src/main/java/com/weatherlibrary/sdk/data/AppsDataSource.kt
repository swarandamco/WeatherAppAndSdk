package com.weatherlibrary.sdk.data

import com.weatherlibrary.sdk.data.model.weather.currentmodels.WeatherResponse
import com.weatherlibrary.sdk.data.model.weather.weekmodels.WeatherWeekResponse
import com.weatherlibrary.sdk.data.network.ApiRestServiceProvider
import com.weatherlibrary.sdk.data.network.ApiService
import io.reactivex.Single

internal class AppsDataSource : DataSource {
    private val apiService: ApiService =
        ApiRestServiceProvider().provideApiservice()

    override fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        tempUnit: String,
        apiKey: String
    ): Single<WeatherResponse> {
        return apiService.getCurrentWeather(latitude, longitude, tempUnit, apiKey)
    }

    override fun getCurrentWeatherforWeek(
        latitude: Double,
        longitude: Double,
        tempUnit: String,
        exclude: String,
        cnt: Int,
        apiKey: String
    ): Single<WeatherWeekResponse> {
        return apiService.getCurrentWeatherforWeek(latitude, longitude, tempUnit, exclude, cnt, apiKey)
    }
}