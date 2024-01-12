package com.example.instachat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.instachat.Utils.FirebaseUtil
import com.example.instachat.databinding.ActivityLoginBinding
import com.example.instachat.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
//        if(!FirebaseUtil.isLoggedIn()) {
//            startActivity(Intent(applicationContext, MainActivity::class.java))
//        }

        setContentView(binding.root)


        binding.loginBtn.setOnClickListener {
            if(binding.loginEmailId.text.equals("") or binding.loginPassword.text.equals("")){
                Toast.makeText(this,"Enter all Details", Toast.LENGTH_LONG).show()
            }else{

                Firebase.auth.signInWithEmailAndPassword(binding.loginEmailId.text.toString(),binding.loginPassword.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this,"Log In Failed",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.signup.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }
}