package com.weatherlibrary.sdk.data.model.weather.weekmodels

data class WeatherWeekResponse (

	val lat : Double,
	val lon : Double,
	val timezone : String,
	val timezone_offset : Int,
	val current : Current,
	val daily : List<Daily>
)