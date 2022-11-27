package com.example.vehiclemaintenanceapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vehiclemaintenanceapplication.databinding.SignInActivityBinding
import com.google.firebase.auth.FirebaseAuth


class SignInActivity : AppCompatActivity() {

    private lateinit var binding: SignInActivityBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences
    object passthrow {
        var pass: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignInActivityBinding.inflate(layoutInflater)
        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        setContentView(binding.root)
//setup intent to move to signup
        firebaseAuth = FirebaseAuth.getInstance()
        binding.textView.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
//listener
        binding.btnRegister.setOnClickListener {
            val email = binding.emailInput.text.toString()
            passthrow.pass = binding.passwordInput.text.toString()

            if (email.isNotEmpty() && passthrow.pass.isNotEmpty()) {
//check if email and pass not empty, allow firebase to sign in or else send toast
                firebaseAuth.signInWithEmailAndPassword(email, passthrow.pass).addOnCompleteListener {
                    if (it.isSuccessful) {

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }

    }



}