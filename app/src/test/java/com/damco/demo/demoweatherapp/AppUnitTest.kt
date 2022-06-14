package com.damco.demo.demoweatherapp

import com.weatherlibrary.sdk.WeatherSDK
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AppUnitTest {

    private lateinit var weatherSDK: WeatherSDK
    private var apiKey = "ae1c4977a943a50eaa7da25e6258d8b2"

    @Before
    fun init() {
        weatherSDK = WeatherSDK.getInstance(apiKey)
    }

    @Test
    fun `test Convert Fahrenheit To Celsius`() {
        val actual: Double = weatherSDK.celsiusToFahrenheit(10.1)
        val expected = -12.16666
        Assert.assertEquals(
            "Conversion from C to F",
            expected,
            actual,
            0.001
        )
    }

    @Test
    fun `test ConvertCelsius To Fahrenheit`() {
        val actual: Double = weatherSDK.fahrenheitToCelsius(11.2)
        val expected = 52.16
        Assert.assertEquals(
            "Conversion from F to C",
            expected,
            actual,
            0.001
        )
    }

    @Test
    fun `test Latitude Longitude`() {
        val status = weatherSDK.validateLatLng(22.11, 44.12)
        Assert.assertEquals(true, status)
    }


}