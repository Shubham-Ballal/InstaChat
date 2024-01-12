package com.example.instachat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.instachat.Models.UserModel
import com.example.instachat.Utils.AndroidUtils
import com.example.instachat.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        if(Firebase.auth.currentUser.toString()!="null"){
            startActivity(Intent(this,MainActivity::class.java))
             finish()
        }
        setContentView(binding.root)

        binding.signupBtn.setOnClickListener {
            if(binding.signupUsername.text.equals("") or binding.signupEmailId.text.equals("") or binding.signupPassword.text.equals("")){
                Toast.makeText(this,"Enter all Details",Toast.LENGTH_LONG).show()
            }else{
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(binding.signupEmailId.text.toString(),binding.signupPassword.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        val user = UserModel(FirebaseAuth.getInstance().uid.toString(),binding.signupUsername.text.toString(),binding.signupEmailId.text.toString(),binding.signupPassword.text.toString())
                        Firebase.firestore.collection("users").document(Firebase.auth.currentUser!!.uid).set(user).addOnSuccessListener {

                            val intent = Intent(this,MainActivity::class.java)
                            AndroidUtils.passUserModelAsIntend(intent,user)
                            startActivity(intent)
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(this,"Sign In Failed",Toast.LENGTH_LONG).show()
                        }
                    }else{
                        Toast.makeText(this,"Sign In Failed",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        binding.login.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}