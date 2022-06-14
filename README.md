# Weather SDK (1.0)

Weather SDK uses APIs provided by [openweathermap](https://openweathermap.org/) which provides
weather related data like rain, snow extreme etc.

## Installation

Installation is very easy, you need to add download the library

from [github](https://github.com/swarandamco/WeatherAppAndSdk/tree/master/weatherlibrary) and paste in the
same app folder in which you want to integrate it, then follow the steps below:

we can also make it gradle dependency using jitpack
https://jitpack.io/

Step 1: Follow to File > New Module
![](https://www.geeksforgeeks.org/how-to-add-a-library-project-to-android-studio/)

Click on “Import Existing Project“.
![](https://media.geeksforgeeks.org/wp-content/uploads/20210327124538/Zm7QO.png)

Step 2: Select the desired library and the desired module. Then click finish. Android Studio will
import the library into your project and will sync Gradle files.

Step 3: In the next step you need to add the imported module to your project’s dependencies.
Right-click on the app folder > Open Module settings

![](https://media.geeksforgeeks.org/wp-content/uploads/20210326232940/Screenshot414.png)

Step 4: Navigate to the dependencies tab > Click on the ‘+’ button -> click on Module Dependency.
The library module will be then added to the project’s dependencies.

![](https://media.geeksforgeeks.org/wp-content/uploads/20210326233244/Screenshot420.png)

it's done, your library is ready to be used!

## Usage 1 : Get Today,s weather data

        val weatherSDK = WeatherSDK.getInstance(getString(R.string.key)) // use your key generated from https://openweathermap.org/

then to fetch current location's weather forecast details for today

        weatherSDK.getCurrentWeather(26.8719, 80.8949,
            WeatherSDK.TempUnit.CELSIUS,
            object : WeatherSDK.WeatherDataListener {
                override fun onWeatherResponse(response: WeatherResponse) {
                val apiResponse ="Weather Today: " + response.name + ", " + response.weather[0].main + ", \n WindSpeed: " + response.wind.speed + ", \n Temp: " + response.main.temp
                    Log.d(TAG, apiResponse)
                }

                override fun onErrorFetchingData(error: Throwable) {
                    Log.d(TAG, error.message.toString())
                }
            })

## Usage 2 : Get weather data for week

To fetch current location's weather forecast details for Week

               weatherSDK.getCurrentWeatherforWeek(27.34345, 77.343543,
                           WeatherSDK.TempUnit.CELSIUS, "hourly,minutely" ,7,
                           object : WeatherSDK.WeatherDataListenerforWeek {
                               override fun onWeatherResponseforWeek(response: WeatherWeekResponse) {
                                   apiResponse = response.toString()
                                   var finalres = ""
                                   response.daily.forEachIndexed { index, data ->
                                                           finalres =
                                                               finalres + "Weather : ${getDatesForWeek(index)}: " + data.weather[0].main + ", " + data.weather[0].description + "\n Max temp " + data.temp.max + ", Min Temp" + data.temp.max + ", \n WindSpeed: " + data.wind_speed + "\n \n"

                                                       }
                                  Log.d(TAG, finalres)
                               }

                               override fun onErrorFetchingData(error: Throwable) {
                               }
                           })


use WeatherSDK.TempUnit.CELSIUS to get temperature in celsius or WeatherSDK.TempUnit.FAHRENHEIT to
get temperature in Fahrenheit

cnt is number of days you want to check for weather report

# Unit test for celsius to fahrenheit and viceversa
# Unit test for validate lat,lng

To run the local unit test cases you need to open the project in Android Studio
>  Now select view as Android
>  Extract com.damco.demo.demoweatherapp(test) package
>  Right click on AppUnitTest class and click on Run 'AppUnitTest'

 You can run every test case individually also
> Open 'AppUnitTest' then right click on particular test case and select Run 'testName'


To run instrumented test cases start emulator or device using ADB to test weather response for same day and week
>  Extract com.damco.demo.demoweatherapp(AndroidTest) package
>  Right click on AndroidAppTest class and click on Run 'AndroidAppTest' or you can run individually every test case.


      
