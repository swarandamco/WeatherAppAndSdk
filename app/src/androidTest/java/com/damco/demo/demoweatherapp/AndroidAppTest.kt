package com.damco.demo.demoweatherapp

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.weatherlibrary.sdk.WeatherSDK
import com.weatherlibrary.sdk.data.model.weather.currentmodels.WeatherResponse
import com.weatherlibrary.sdk.data.model.weather.weekmodels.WeatherWeekResponse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@SmallTest
class AndroidAppTest {

    private lateinit var weatherSDK: WeatherSDK
    private var apiKey = "ae1c4977a943a50eaa7da25e6258d8b2"

    @Before
    fun init() {
        weatherSDK = WeatherSDK.getInstance(apiKey)
    }

    // Test case to check response for current day
    @Test
    fun testWeatherResponseforsameday() {
        var apiResponse: WeatherResponse? = null
        if (weatherSDK == null) {
            weatherSDK = WeatherSDK.getInstance(apiKey)
            Thread.sleep(1000)
        }

        weatherSDK.getCurrentWeather(27.34345, 77.343543,
            WeatherSDK.TempUnit.CELSIUS,
            object : WeatherSDK.WeatherDataListener {
                override fun onWeatherResponse(response: WeatherResponse) {
                    apiResponse = response
                }

                override fun onErrorFetchingData(error: Throwable) {
                }
            })
        Thread.sleep(5000)
        assertThat(apiResponse?.cod).isEqualTo(200)

    }


    // Test case to check response for week
    @Test
    fun testWeatherResponseforWeek() {
        var apiResponse: WeatherWeekResponse? = null
        if (weatherSDK == null) {
            weatherSDK = WeatherSDK.getInstance(apiKey)
            Thread.sleep(1000)
        }
        weatherSDK.getCurrentWeatherforWeek(27.34345, 77.343543,
            WeatherSDK.TempUnit.CELSIUS, "hourly,minutely", 7,
            object : WeatherSDK.WeatherDataListenerforWeek {
                override fun onWeatherResponseforWeek(response: WeatherWeekResponse) {
                    apiResponse = response
                }

                override fun onErrorFetchingData(error: Throwable) {
                }
            })

        Thread.sleep(5000)
        assertThat(apiResponse?.daily?.size).isEqualTo(8)

    }

}