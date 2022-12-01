package com.example.vehiclemaintenanceapplication

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import com.example.vehiclemaintenanceapplication.Backend.CarViewModel
import com.example.vehiclemaintenanceapplication.Backend.CarViewModelFactory
import com.example.vehiclemaintenanceapplication.Backend.Repository.Repository
import com.example.vehiclemaintenanceapplication.WeatherCode.POJO.Main


class Maintenance : Fragment() {
   private lateinit var viewModel: CarViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater?.inflate(R.layout.maintenance_frag,
            container, false)



        val repository = Repository()
        val viewModelFactory = CarViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CarViewModel::class.java)
        viewModel.getObd(MainActivity.faultcode.faultcode)
        viewModel.myResponse3.observe(viewLifecycleOwner, Observer { response3 ->

            val text1 = view.findViewById<TextView>(R.id.obdcode)
            val text2 = view.findViewById<TextView>(R.id.maintenancetipuno)
            val text3 = view.findViewById<TextView>(R.id.problems1)
            val text4 = view.findViewById<TextView>(R.id.solution1)
            val text5 = view.findViewById<TextView>(R.id.problems)
            val text6 = view.findViewById<TextView>(R.id.solution)
            var imageview3 = view.findViewById<ImageView>(R.id.imageView3)

            Log.d("Main", "${MainActivity.faultcode.faultcode}")
            if(response3.isSuccessful && MainActivity.count.count==1) {
                text1.setText("OBD Code: " + response3.body()?.code).toString()
                text2.setText("Description: "+response3.body()?.descrip).toString()
                text3.setText("Description: "+response3.body()?.issues).toString()
                text4.setText("Description: "+response3.body()?.solution).toString()


            }else {
                text1.setText("NO ISSUES DETECTED").toString()
                text2.setText("").toString()
                text3.setText("").toString()
                text4.setText("").toString()
                text5.setText("").toString()
                text6.setText("").toString()
                imageview3.setBackgroundResource(R.drawable.check)

            }





        })

        return view
    }
}
