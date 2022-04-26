package com.example.vehiclemaintenanceapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {



    val ref=FirebaseAuth.getInstance()


    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)








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
                R.id.nav_home -> replaceFragment(HomeFragment(), it.title.toString())
                R.id.nav_logout -> firebaseAuth.signOut()
                R.id.nav_weather -> startActivity(Intent(this, WeatherNode::class.java))
                R.id.nav_sync -> Toast.makeText(applicationContext, "Synced",Toast.LENGTH_SHORT).show()
                R.id.nav_Maintenance -> replaceFragment(Maintenance(), it.title.toString())
                R.id.nav_settings -> replaceFragment(settingsFragment(), it.title.toString())
            }

            true
        }

    }
    //function i made to let me move from menu to fragments
    private fun replaceFragment(fragment: Fragment, title: String){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}