package com.example.vehiclemaintenanceapplication

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vehiclemaintenanceapplication.databinding.SignupActivityBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var binding:SignupActivityBinding
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences
    object eminthrow {
        var email: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity)


        binding = SignupActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
//listener on textview
        binding.textView.setOnClickListener{
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
        binding.btnRegister.setOnClickListener{
            eminthrow.email = binding.emailInput.text.toString()
            val conpass = binding.confirmpasswordInput.text.toString()
            SignInActivity.passthrow.pass = binding.passwordInput.text.toString()



//check if email, pass and conpass aren't empty, if they are it won't work
            if(eminthrow.email.isNotEmpty() && SignInActivity.passthrow.pass.isNotEmpty() && conpass.isNotEmpty()){
                if(SignInActivity.passthrow.pass == (conpass)){

                    firebaseAuth.createUserWithEmailAndPassword(eminthrow.email, SignInActivity.passthrow.pass).addOnCompleteListener{
                        if(it.isSuccessful){

                            val intent = Intent(this, CarSelect::class.java)
                            startActivity(intent)
                            finish()

                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this, "Passwords do not match" , Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Empty fields are not allowed" , Toast.LENGTH_SHORT).show()
            }
        }
    }

}