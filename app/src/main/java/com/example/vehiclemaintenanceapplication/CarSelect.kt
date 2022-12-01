package com.example.vehiclemaintenanceapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vehiclemaintenanceapplication.Backend.CarUserPost
import com.example.vehiclemaintenanceapplication.Backend.CarViewModel
import com.example.vehiclemaintenanceapplication.Backend.CarViewModelFactory
import com.example.vehiclemaintenanceapplication.Backend.Repository.Repository
import com.example.vehiclemaintenanceapplication.databinding.CarSelectBinding

class CarSelect : AppCompatActivity()  {

    private lateinit var binding: CarSelectBinding
    private lateinit var viewModel: CarViewModel
    private lateinit var spin1: String
    object car{
        var make=""
        var model=""
    }
//bind car select screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //view binding for carselect
        var binding = CarSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)
//car make array


//listener on button to send to mainactivity
        binding.button.setOnClickListener(){
            signup()


//controls where button will send
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

    }
//function to get signup info from user and post to server
    private fun signup() {
        var name = ""
        val checker = findViewById<TextView>(R.id.name_input)
        if(checker.text.isNotEmpty()){
           name = checker.text.toString()
        }
        var year = ""
        val checkyear = findViewById<TextView>(R.id.year_input)
        if(checkyear.text.isNotEmpty()){
           year = checkyear.text.toString()
        }
         car.make = "Ford"
        val checkmake = findViewById<TextView>(R.id.makecar_input)
        if(checkmake.text.isNotEmpty()){
            car.make=checkmake.text.toString()
        }
         car.model = "Explorer"
        val checkmodel = findViewById<TextView>(R.id.modelcar_input)
        if(checkmodel.text.isNotEmpty()){
            car.model=checkmodel.text.toString()
        }


        val repository = Repository()
        val viewModelFactory = CarViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CarViewModel::class.java)
        val myPost = CarUserPost("$name", "${SignupActivity.eminthrow.email}", "${SignInActivity.passthrow.pass}", "${car.make}", "${car.model}", "$year")
        viewModel.pushPost(myPost)
        viewModel.myResponse4.observe(this, Observer { response4 ->
            if (response4.isSuccessful) {
                Log.d("Main", response4.body().toString())
                Log.d("Main", response4.code().toString())
                Log.d("Main", response4.message())
            }


        })


    }


}