package com.example.instachat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.example.instachat.Models.UserModel
import com.example.instachat.databinding.ActivityShowPostBinding
import com.example.instachat.databinding.PostRvBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ShowPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityShowPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(intent.getStringExtra("postUri")).into(binding.post)
    }
}