package com.example.instachat.Post

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.instachat.Models.PostModel
import com.example.instachat.Models.ReelModel
import com.example.instachat.Models.UserModel
import com.example.instachat.R
import com.example.instachat.Utils.FirebaseUtil
import com.example.instachat.Utils.FirebaseUtil.Companion.currentUserUid
import com.example.instachat.databinding.ActivityPostBinding
import com.example.instachat.databinding.ActivityReelsBinding
import com.example.instachat.databinding.MypostRvDesignBinding
import com.example.instachat.databinding.MyreelRvDesignBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage

class ReelsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReelsBinding
    private lateinit var selectedReelUri: Uri
    private var videoPicLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            selectedReelUri = it
            Glide.with(this).load(it).into(binding.newpostImgView)
        } ?: Toast.makeText(this, "Failed to load video", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newreelCancelBtn.setOnClickListener { onBackPressed() }
        binding.newreelBackbtn.setOnClickListener { onBackPressed() }
        binding.newreelPostBtn.setOnClickListener { uploadReel() }
        binding.newreelSelectBtn.setOnClickListener { selectVideo() }
    }

    private fun selectVideo() {
        videoPicLauncher.launch("video/*")
    }

    private fun uploadReel() {
        if (::selectedReelUri.isInitialized) {
            FirebaseStorage.getInstance().getReference().child("reels").child(FirebaseUtil.currentUserUid()).listAll().addOnSuccessListener { listResult ->
                val itemCount = listResult.items.size

                FirebaseStorage.getInstance().getReference().child("reels").child(FirebaseUtil.currentUserUid()).child(itemCount.toString()).putFile(selectedReelUri).addOnSuccessListener {
                    FirebaseStorage.getInstance().getReference("reels/" + FirebaseUtil.currentUserUid() + "/" + itemCount.toString()).downloadUrl.addOnSuccessListener { uri ->
                        val reel = ReelModel(uri.toString(), binding.newreelCaption.text.toString(), currentUserUid())

                        Firebase.firestore.collection("users").document(currentUserUid()).get().addOnSuccessListener {
                            val user = it.toObject<UserModel>()
                            Log.d("testing", user?.postCount.toString())
                            Firebase.firestore.collection("users").document(currentUserUid()).update("postCount", user?.postCount?.plus(1))
                            Log.d("testing", user?.postCount?.plus(1).toString())
                        }

                        Firebase.firestore.collection("reels").add(reel)
                        finish()
                    }
                }
            }
        }
        else{
            Toast.makeText(this, "Please select a video first", Toast.LENGTH_SHORT).show()
        }
    }
}