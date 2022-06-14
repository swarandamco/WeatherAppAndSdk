package com.damco.demo.demoweatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.damco.demo.demoweatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import com.weatherlibrary.sdk.WeatherSDK
import com.weatherlibrary.sdk.data.model.weather.currentmodels.WeatherResponse
import com.weatherlibrary.sdk.data.model.weather.weekmodels.WeatherWeekResponse
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var weatherSDK: WeatherSDK
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var binding: ActivityMainBinding
    private var latitude = 0.0
    private var longitude = 0.0
    private var exclude = "hourly,minutely"
    private var cnt = 7
    private var temp=WeatherSDK.TempUnit.FAHRENHEIT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        weatherSDK = WeatherSDK.getInstance(getString(R.string.key))
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()


        binding.content.today.setOnClickListener {
            getCurrentWeather()
        }

        binding.content.week.setOnClickListener {
            getCurrentWeatherforWeek()
        }
        
        binding.content.temp.setOnCheckedChangeListener { buttonView, isChecked ->
            temp = if (isChecked) {
                WeatherSDK.TempUnit.CELSIUS
            } else {
                WeatherSDK.TempUnit.FAHRENHEIT
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() { // check if permissions are given
        if (checkPermissions()) {
            // check if location is enabled
            if (isLocationEnabled()) {
                fusedLocationProviderClient?.lastLocation?.addOnCompleteListener { task ->
                    val location = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        setLocation(location)
                    }

                }
            } else {
                goToLocationSettings()

            }
        } else {
// if permissions aren't available,
            // request for permissions
            requestPermissions()
        }
    }

    private fun goToLocationSettings() {
        Toast.makeText(this, "Please turn on your location...", Toast.LENGTH_LONG)
            .show()
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    private fun setLocation(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
        val testData = "latitude ${location.latitude}, longitude ${location.longitude} "
        binding.content.tvLocation.text = testData

    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        // Initializing LocationRequest
        // object with appropriate methods
        val locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1

        // setting LocationRequest
        // on FusedLocationClient

        // setting LocationRequest
        // on FusedLocationClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            setLocation(mLastLocation)
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )

    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                goToLocationSettings()
            }
        }
    }

    // method to request for permissions
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_ID
        )
    }

    private fun getCurrentWeather() {
        weatherSDK.getCurrentWeather(latitude, longitude,
            temp,
            object : WeatherSDK.WeatherDataListener {
                override fun onWeatherResponse(response: WeatherResponse) {
                    val finalres =
                        "Weather Today: " + response.name + ", " + response.weather[0].main + ", \n WindSpeed: " + response.wind.speed + ", \n Temp: " + response.main.temp
                    openAlertDialog("Weather Today", finalres)
                }

                override fun onErrorFetchingData(error: Throwable) {

                    openAlertDialog("Weather Today", error.message.toString())
                }
            })

    }


    private fun getCurrentWeatherforWeek() {
        weatherSDK.getCurrentWeatherforWeek(latitude, longitude,
            temp,
            exclude,
            cnt,
            object : WeatherSDK.WeatherDataListenerforWeek {
                override fun onWeatherResponseforWeek(response: WeatherWeekResponse) {
//                    for(response.)
//                    val listofWeekSize = response.daily.size
                    var finalres = ""

//                    for(i:Int = 0; i<response.daily)
                    response.daily.forEachIndexed { index, data ->
//                        for (data in response.daily) {
                        finalres =
                            finalres + "Weather : ${getDatesForWeek(index)}: " + data.weather[0].main + ", " + data.weather[0].description + "\n Max temp " + data.temp.max + ", Min Temp" + data.temp.max + ", \n WindSpeed: " + data.wind_speed + "\n \n"
//                        }

                    }
                    openAlertDialog("Weather for Week", finalres)
                }

                override fun onErrorFetchingData(error: Throwable) {
                    openAlertDialog("Weather for Week", error.message.toString())
                }
            })

    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            getLastLocation()
        }
    }

    companion object {
//        private const val TAG = "MainActivity"
        private const val PERMISSION_ID = 44
    }

    /**
     *
     */
    fun openAlertDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        var alertDialog: AlertDialog? = null
        builder.setTitle(title)
        builder.setMessage(message)

        //performing positive action
        builder.setPositiveButton("Ok") { dialogInterface, which ->
            alertDialog?.dismiss()
        }
        alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    /**
     *
     */
    fun getDatesForWeek(amount: Int): String {
        val dateFormat = "MM/dd/yyyy"
        val cal = Calendar.getInstance()
//        cal.time = Date(currentTime)
        cal.add(Calendar.DAY_OF_MONTH, amount)
        val date = cal.time
        //EEE MMM dd HH:mm:ss z yyyy
        val formatter = SimpleDateFormat(dateFormat)
        formatter.isLenient = false
        return formatter.format(date)
    }

}