package com.example.vehiclemaintenanceapplication

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.vehiclemaintenanceapplication.WeatherCode.POJO.ModelClass
import com.example.vehiclemaintenanceapplication.WeatherCode.Utilities.ApiUtilities
import com.example.vehiclemaintenanceapplication.databinding.WeatherBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*


class WeatherNode : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var weatherBinding: WeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather)

        weatherBinding=DataBindingUtil.setContentView(this,R.layout.weather)
        supportActionBar?.hide()
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this)
        weatherBinding.rlMainLayout.visibility= View.GONE

        getCurrentLocation()
//call get location function
        //setup for search bar info
        weatherBinding.etGetCityName.setOnEditorActionListener( { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getCityWeather(weatherBinding.etGetCityName.text.toString())
                val view = this.currentFocus
                if (view != null) {
                    val imm: InputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                    weatherBinding.etGetCityName.clearFocus()
                }
                true
            }else
                false
        })

    }
//get city info for weather and apply to our data below
    private fun getCityWeather(cityName: String) {
        weatherBinding.pbLoading.visibility=View.VISIBLE
        ApiUtilities.getApiInterface()?.getCityWeatherData(cityName, API_KEY)?.enqueue(object :Callback<ModelClass>{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<ModelClass>, response: Response<ModelClass>) {

                    setDataOnViews(response.body())

            }

            override fun onFailure(call: Call<ModelClass>, t: Throwable) {
                Toast.makeText(applicationContext,"City name is not valid",Toast.LENGTH_SHORT).show()
            }
        })
    }
//use user settings to get location, if not available ask
    private fun getCurrentLocation(){

        if(checkPermissions()){
            if(isLocationEnabled()){


                if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!=
                    PackageManager.PERMISSION_GRANTED){
                    requestPermission()
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this) {
                    task -> val location: Location?=task.result
                    if(location == null){
                        Toast.makeText(this, "Nothing Received", Toast.LENGTH_SHORT).show()
                    }else{
                        fetchCurrentLocationWeather(location.latitude.toString(),location.longitude.toString())

                    }
                }

            }else{
                Toast.makeText(this, "Turn on location", Toast.LENGTH_SHORT).show()
                val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }else{
            requestPermission()

        }



    }
//use user location and weather api to find local weather info
    private fun fetchCurrentLocationWeather(latitude: String, longitude: String) {

        weatherBinding.pbLoading.visibility=View.VISIBLE
        ApiUtilities.getApiInterface()?.getCurrentWeatherdata(latitude,longitude,API_KEY)?.enqueue(object :Callback<ModelClass>{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<ModelClass>, response: Response<ModelClass>) {
                if(response.isSuccessful){
                    setDataOnViews(response.body())
                }
            }

            override fun onFailure(call: Call<ModelClass>, t: Throwable) {
                Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
            }
        })

    }
//setup data for views,
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDataOnViews(body: ModelClass?) {

        val sdf=SimpleDateFormat("dd/MM/yyyy hh:mm")
        val currentDate=sdf.format(Date())
        weatherBinding.tvDateAndTime.text=currentDate

        weatherBinding.tvMaxTemp.text="Day "+kelvinToFahrenheit(body!!.main.temp_max) +"°"
        weatherBinding.tvMinTemp.text="Night "+kelvinToFahrenheit(body!!.main.temp_min) +"°"
        weatherBinding.tvMinTemp.text=""+kelvinToFahrenheit(body!!.main.temp) +"°"
        weatherBinding.tvFeelsLike.text="Feels like "+kelvinToFahrenheit(body!!.main.feels_like) +"°"
        weatherBinding.tvWeatherType.text=body.weather[0].main
        weatherBinding.tvSunrise.text=timeStampToLocalDate(body.sys.sunrise.toLong())
        weatherBinding.tvSunset.text=timeStampToLocalDate(body.sys.sunset.toLong())
        weatherBinding.tvPressure.text=body.main.pressure.toString()
        weatherBinding.tvHumidity.text=body.main.humidity.toString()+" %"
        weatherBinding.tvWindSpeed.text=body.wind.speed.toString()+" m/s"

        weatherBinding.tvTempF.text=""+((kelvinToCelsius(body.main.temp)))
        weatherBinding.etGetCityName.setText(body.name)

        updateUI(body.weather[0].id)

    }
//all of these are based on api where they categorize weather type based on a number, then assign variables based on number
    private fun updateUI(id: Int) {
        if(id in 200..232) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.thunderstorm)
            weatherBinding.rlToolbar.setBackgroundColor(resources.getColor(R.color.thunderstorm))
            weatherBinding.rlSubLayout.background =
                ContextCompat.getDrawable(this@WeatherNode, R.drawable.thunderstorm_bg)
            weatherBinding.llMainBgBelow.background =
                ContextCompat.getDrawable(this@WeatherNode, R.drawable.thunderstorm_bg)
            weatherBinding.llMainBgAbove.background =
                ContextCompat.getDrawable(this@WeatherNode, R.drawable.thunderstorm_bg)
            weatherBinding.ivWeatherBg.setImageResource(R.drawable.thunderstorm_bg)
            weatherBinding.ivWeatherBg.setImageResource(R.drawable.thunderstorm)
        }else if (id in 300..321){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = resources.getColor(R.color.drizzle)
            weatherBinding.rlToolbar.setBackgroundColor(resources.getColor(R.color.drizzle))
            weatherBinding.rlSubLayout.background = ContextCompat.getDrawable(this@WeatherNode, R.drawable.drizzle_bg)
            weatherBinding.llMainBgBelow.background=ContextCompat.getDrawable(this@WeatherNode,R.drawable.drizzle_bg)
            weatherBinding.llMainBgAbove.background=ContextCompat.getDrawable(this@WeatherNode,R.drawable.drizzle_bg)
            weatherBinding.ivWeatherBg.setImageResource(R.drawable.drizzle_bg)
            weatherBinding.ivWeatherIcon.setImageResource(R.drawable.drizzle)

        }else if(id in 500..531){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor=resources.getColor(R.color.rain)
            weatherBinding.rlToolbar.setBackgroundColor(resources.getColor(R.color.rain))
            weatherBinding.rlSubLayout.background=ContextCompat.getDrawable(this@WeatherNode,R.drawable.rainy_bg)
            weatherBinding.llMainBgBelow.background = ContextCompat.getDrawable(this@WeatherNode,R.drawable.rainy_bg)
            weatherBinding.llMainBgAbove.background = ContextCompat.getDrawable(this@WeatherNode,R.drawable.rainy_bg)
            weatherBinding.ivWeatherBg.setImageResource(R.drawable.rainy_bg)
            weatherBinding.ivWeatherIcon.setImageResource(R.drawable.rain)
        }else if(id in 600..620){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor=resources.getColor(R.color.snow)
            weatherBinding.rlToolbar.setBackgroundColor(resources.getColor(R.color.snow))
            weatherBinding.rlSubLayout.background=ContextCompat.getDrawable(this@WeatherNode,R.drawable.snow_bg)
            weatherBinding.llMainBgBelow.background = ContextCompat.getDrawable(this@WeatherNode,R.drawable.snow_bg)
            weatherBinding.llMainBgAbove.background = ContextCompat.getDrawable(this@WeatherNode,R.drawable.snow_bg)
            weatherBinding.ivWeatherBg.setImageResource(R.drawable.snow_bg)
            weatherBinding.ivWeatherIcon.setImageResource(R.drawable.snow)
        }else if(id in 701..781){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor=resources.getColor(R.color.atmosphere)
            weatherBinding.rlToolbar.setBackgroundColor(resources.getColor(R.color.atmosphere))
            weatherBinding.rlSubLayout.background=ContextCompat.getDrawable(this@WeatherNode,R.drawable.mist_bg)
            weatherBinding.llMainBgBelow.background = ContextCompat.getDrawable(this@WeatherNode,R.drawable.mist_bg)
            weatherBinding.llMainBgAbove.background = ContextCompat.getDrawable(this@WeatherNode,R.drawable.mist_bg)
            weatherBinding.ivWeatherBg.setImageResource(R.drawable.mist_bg)
            weatherBinding.ivWeatherIcon.setImageResource(R.drawable.mist)
        }else if(id == 800){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor=resources.getColor(R.color.clear)
            weatherBinding.rlToolbar.setBackgroundColor(resources.getColor(R.color.clear))
            weatherBinding.rlSubLayout.background=ContextCompat.getDrawable(this@WeatherNode,R.drawable.clear_bg)
            weatherBinding.llMainBgBelow.background = ContextCompat.getDrawable(this@WeatherNode,R.drawable.clear_bg)
            weatherBinding.llMainBgAbove.background = ContextCompat.getDrawable(this@WeatherNode,R.drawable.clear_bg)
            weatherBinding.ivWeatherBg.setImageResource(R.drawable.clear_bg)
            weatherBinding.ivWeatherIcon.setImageResource(R.drawable.clear)
        }else{
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor=resources.getColor(R.color.clouds)
            weatherBinding.rlToolbar.setBackgroundColor(resources.getColor(R.color.clouds))
            weatherBinding.rlSubLayout.background=ContextCompat.getDrawable(this@WeatherNode,R.drawable.clouds_bg)
            weatherBinding.llMainBgBelow.background = ContextCompat.getDrawable(this@WeatherNode,R.drawable.clouds_bg)
            weatherBinding.llMainBgAbove.background = ContextCompat.getDrawable(this@WeatherNode,R.drawable.clouds_bg)
            weatherBinding.ivWeatherBg.setImageResource(R.drawable.clouds_bg)
            weatherBinding.ivWeatherIcon.setImageResource(R.drawable.clouds)
        }
        weatherBinding.pbLoading.visibility=View.GONE
        weatherBinding.rlMainLayout.visibility=View.VISIBLE
    }
//simple kelvin to celsius
    private fun kelvinToCelsius(temp: Double): Double {
        var intTemp = temp
        intTemp=intTemp.minus(273.15)
        return intTemp.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
    }
//grab local time from phone
    @RequiresApi(Build.VERSION_CODES.O)
    private fun timeStampToLocalDate(timeStamp: Long): String {
        val localTime = timeStamp.let {
            Instant.ofEpochSecond(it).atZone(ZoneId.systemDefault()).toLocalTime()

        }
        return localTime.toString()
    }
//kelvin to fahrenheit thing
    private fun kelvinToFahrenheit(temp: Double): Double {
        var intTemp = temp
        intTemp = intTemp.minus(273.15)
        intTemp = intTemp.times(9/5)
        intTemp = intTemp.plus(32)
        return intTemp.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()

    }
//checks phone for location services
    private fun isLocationEnabled():Boolean{
        val locationManager:LocationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
//request permission for location services
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION)
            , PERMISSION_REQUEST_ACCESS_LOCATION

        )
    }
//api
    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION=100
        const val API_KEY="b7947718be67978e25daf83d0140ebcd"
    }
//as stated checks location permissions
    private fun checkPermissions(): Boolean{
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)==
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }
//request permission, based on this either continue or say denied
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode== PERMISSION_REQUEST_ACCESS_LOCATION){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext, "Granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            }else{
                Toast.makeText(applicationContext, "Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}