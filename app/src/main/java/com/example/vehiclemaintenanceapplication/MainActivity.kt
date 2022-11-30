package com.example.vehiclemaintenanceapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vehiclemaintenanceapplication.Backend.CarUserPost
import com.example.vehiclemaintenanceapplication.Backend.CarViewModel
import com.example.vehiclemaintenanceapplication.Backend.CarViewModelFactory
import com.example.vehiclemaintenanceapplication.Backend.Repository.Repository
import com.example.vehiclemaintenanceapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.FirebaseAuth
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class MainActivity : AppCompatActivity() {



    val ref=FirebaseAuth.getInstance()
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CarViewModel
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var mainHandle: Handler
    private val updateText = object : Runnable {
        override fun run() {
            update()
            mainHandle.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//setup for navigation bar, drawer layout allows for navigation to be pulled from side of screen
        drawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)
//setup toggle button
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//listener on toggle button
        navView.setNavigationItemSelectedListener {

            it.isChecked = true
            when(it.itemId){
//when clicked will do the action below
                R.id.nav_home -> startActivity(Intent(this, MainActivity::class.java))
                R.id.nav_logout -> logout()
                R.id.nav_weather -> startActivity(Intent(this, WeatherNode::class.java))
                R.id.alerts -> startActivity(Intent(this, Alerts::class.java))
                R.id.nav_Maintenance -> replaceFragment(Maintenance(), it.title.toString())
                R.id.nav_settings -> replaceFragment(settingsFragment(), it.title.toString())
            }

            true
        }

        val repository = Repository()
        val viewModelFactory = CarViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CarViewModel::class.java)




        mainHandle = Handler(Looper.getMainLooper())








        val myPost = CarUserPost("Zach Price", "zachprice@tamu.edu", "Test103122", "Ford", "F-150", "2011")
        /*
        viewModel.pushPost(myPost)
        viewModel.myResponse4.observe(this, Observer { response4 ->
            if(response4.isSuccessful){
                Log.d("Main", response4.body().toString())
                Log.d("Main", response4.code().toString())
                Log.d("Main", response4.message())
            }
        })
*/
    }






    //function i made to let me move from menu to fragments
    private fun replaceFragment(fragment: Fragment, title: String){





        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.constraintreplace,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)

    }
    fun update(){


        viewModel.getPost(SignInActivity.passthrow.pass)
        viewModel.myResponse.observe(this, Observer { response ->


            val text1 = findViewById<TextView>(R.id.user_name)
            text1.setText(response.name).toString()
            val text2 = findViewById<TextView>(R.id.emin)
            text2.setText(response.user_email).toString()
            //val textma = findViewById<TextView>(R.id.cartype)
            //textma.setText(response.user_vehicle_year + " " + response.user_vehicle_make + " " + response.user_vehicle_model)
                //.toString()


        })

        var current = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString()
        Log.d("Main", "$current")
        viewModel.getData(current)
        viewModel.myResponse2.observe(this, Observer { response2 ->
            if(response2.isSuccessful) {

                val text3 = findViewById<TextView>(R.id.speed)
                text3.setText("Speed: " + response2.body()?.speed)
                val text4 = findViewById<TextView>(R.id.rpm)
                text4.setText("RPM: " + response2.body()?.rpm)
                val text5 = findViewById<TextView>(R.id.oitem)
                text5.setText("Oil Temp: " + response2.body()?.oilTemp)
                val text6 = findViewById<TextView>(R.id.cootem)
                text6.setText("Coolant Temp: " + response2.body()?.coolant)
                val text7 = findViewById<TextView>(R.id.fpres)
                text7.setText("Fuel Pressure: " + response2.body()?.fuelPressure)
                val text8 = findViewById<TextView>(R.id.runt)
                text8.setText("Runtime: " + response2.body()?.engineRuntime)
                val text9 = findViewById<TextView>(R.id.lfuelt1)
                text9.setText("Fuel Trim 1(long): " + response2.body()?.longfueltrim1)
                val text10 = findViewById<TextView>(R.id.lfuelt2)
                text10.setText("Fuel Trim 2(long): " + response2.body()?.longfueltrim2)
                val text11 = findViewById<TextView>(R.id.o21)
                text11.setText("Bank 1 Voltage: " + response2.body()?.o2bank1)
                val text12 = findViewById<TextView>(R.id.o22)
                text12.setText("Bank 2 Voltage: " + response2.body()?.o2bank2)
                val text13 = findViewById<TextView>(R.id.sftrim1)
                text13.setText("Fuel Trim 1(short): " + response2.body()?.shortfueltrim1)
                val text14 = findViewById<TextView>(R.id.sftrim2)
                text14.setText("Fuel Trim 2(short): " + response2.body()?.shortfueltrim2)
            }

        })
    }
    override fun onPause() {
        super.onPause()
        mainHandle.removeCallbacks(updateText)
    }

    override fun onResume() {
        super.onResume()
        mainHandle.post(updateText)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun logout(){
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

}