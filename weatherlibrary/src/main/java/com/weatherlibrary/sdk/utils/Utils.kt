package com.weatherlibrary.sdk.utils

import com.weatherlibrary.sdk.WeatherSDK

object Utils {
    fun getConvertedUnit(tempUnit: WeatherSDK.TempUnit): String {
        return when (tempUnit.name) {
            WeatherSDK.TempUnit.CELSIUS.name -> {
                "Metric"
            }
            WeatherSDK.TempUnit.FAHRENHEIT.name -> {
                "Imperial"
            }
            else -> {
                "Standard"
            }
        }
    }

}