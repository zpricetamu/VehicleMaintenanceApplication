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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //view binding for carselect
        var binding = CarSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)
//car make array


        val arrayList_make= arrayListOf<String>("Please the make of your vehicle", "Toyota", "Volkswagen", "Ford", "Honda")
        val arrayAdapter_make=ArrayAdapter(applicationContext,R.layout.carmake,arrayList_make)


//model arrays for car
        val arraymodelstoyota = arrayListOf<String>("Please select model of vehicle", "Camry", "Corolla", "Highlander", "RAV4", "Tacoma")
        val arraymodelsvolk = arrayListOf<String>("Please select model of vehicle", "Beetle", "Jetta", "Tiguan")
        val arraymodelsford = arrayListOf<String>("Please select model of vehicle", "Explorer", "F-150", "Fiesta", "Focus", "Mustang")
        val arraymodelshonda = arrayListOf<String>("Please select model of vehicle", "Accord", "Civic", "CR-V", "Odyssey", "Pilot")
        val aamodel=ArrayAdapter(applicationContext,R.layout.carmodel,arraymodelstoyota)
        //sent spinner 2 = to aamodel





//assign aamodel depen










//listener on button to send to mainactivity
        binding.button.setOnClickListener(){
            signup()



            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

    }

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
        var make = "Ford"
        val checkmake = findViewById<TextView>(R.id.makecar_input)
        if(checkmake.text.isNotEmpty()){
            make=checkmake.text.toString()
        }
        var model = "Explorer"
        val checkmodel = findViewById<TextView>(R.id.modelcar_input)
        if(checkmodel.text.isNotEmpty()){
            model=checkmodel.text.toString()
        }


        val repository = Repository()
        val viewModelFactory = CarViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CarViewModel::class.java)
        val myPost = CarUserPost("$name", "${SignupActivity.eminthrow.email}", "${SignInActivity.passthrow.pass}", "$make", "$model", "$year")
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