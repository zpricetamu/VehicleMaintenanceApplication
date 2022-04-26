package com.example.vehiclemaintenanceapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vehiclemaintenanceapplication.POJO.Main
import com.example.vehiclemaintenanceapplication.databinding.CarSelectBinding

class CarSelect : AppCompatActivity()  {

    private lateinit var binding: CarSelectBinding
    private val savedpref="savedPref"
    private val NAME = "name"
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
                    val aamodel=ArrayAdapter(applicationContext,R.layout.carmodel,arraymodelstoyota)
                    binding.spinner2.adapter=aamodel
                }
                if(p2==2){
                    val aamodel=ArrayAdapter(applicationContext,R.layout.carmodel,arraymodelsvolk)
                    binding.spinner2.adapter=aamodel
                }
                if(p2==3){
                    val aamodel=ArrayAdapter(applicationContext,R.layout.carmodel,arraymodelsford)
                    binding.spinner2.adapter=aamodel
                }
                if(p2==4){
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
            //saveData()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

//my work for sharedpref caused to many issues with not enough time to fix
   /* private fun saveData() {
        val insertname=binding.name.toString()


        val sharedPref = getSharedPreferences(savedpref, Context.MODE_PRIVATE)

        val editor = sharedPref.edit()
        editor.putString(NAME, insertname)
        editor.apply()

        Toast.makeText(this, "it works dumbass", Toast.LENGTH_SHORT).show()

    }
*/
}