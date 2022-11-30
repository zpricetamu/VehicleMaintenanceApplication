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
        binding.spinner.adapter = arrayAdapter_make

//model arrays for car
        val arraymodelstoyota = arrayListOf<String>("Please select model of vehicle", "Camry", "Corolla", "Highlander", "RAV4", "Tacoma")
        val arraymodelsvolk = arrayListOf<String>("Please select model of vehicle", "Beetle", "Jetta", "Tiguan")
        val arraymodelsford = arrayListOf<String>("Please select model of vehicle", "Explorer", "F-150", "Fiesta", "Focus", "Mustang")
        val arraymodelshonda = arrayListOf<String>("Please select model of vehicle", "Accord", "Civic", "CR-V", "Odyssey", "Pilot")
        val aamodel=ArrayAdapter(applicationContext,R.layout.carmodel,arraymodelstoyota)
        //sent spinner 2 = to aamodel
        binding.spinner2.adapter=aamodel




//assign aamodel depending on which make


        binding.spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2==1){
                    spin1 = p0?.getItemAtPosition(p2).toString()
                    val aamodel=ArrayAdapter(applicationContext,R.layout.carmodel,arraymodelstoyota)

                    binding.spinner2.adapter=aamodel

                }
                if(p2==2){
                    spin1 = p0?.getItemAtPosition(p2).toString()
                    val aamodel=ArrayAdapter(applicationContext,R.layout.carmodel,arraymodelsvolk)

                    binding.spinner2.adapter=aamodel
                }
                if(p2==3){
                    spin1 = p0?.getItemAtPosition(p2).toString()
                    val aamodel=ArrayAdapter(applicationContext,R.layout.carmodel,arraymodelsford)

                    binding.spinner2.adapter=aamodel
                }
                if(p2==4){
                    spin1 = p0?.getItemAtPosition(p2).toString()
                    val aamodel=ArrayAdapter(applicationContext,R.layout.carmodel,arraymodelshonda)

                    binding.spinner2.adapter=aamodel
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }



        }




//listener on button to send to mainactivity
        binding.button.setOnClickListener(){
            signup()



            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

    }

    private fun signup() {
        var name = "New User"
        val checker = findViewById<TextView>(R.id.name_input)
        if(checker.text.isNotEmpty()){
            name = checker.toString()
        }
        var year = ""
        val checkyear = findViewById<TextView>(R.id.year_input)
        if(checkyear.text.isNotEmpty()){
            year = checkyear.toString()
        }



            val repository = Repository()
            val viewModelFactory = CarViewModelFactory(repository)
            viewModel = ViewModelProvider(this, viewModelFactory).get(CarViewModel::class.java)
            val myPost = CarUserPost("$name", "${SignupActivity.eminthrow.toString()}", "${SignInActivity.passthrow.toString()}", "$spin1", "working on this", "$year")
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