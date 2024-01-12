package com.example.instachat.Post

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.instachat.Models.PostModel
import com.example.instachat.Models.UserModel
import com.example.instachat.R
import com.example.instachat.Utils.FirebaseUtil
import com.example.instachat.databinding.ActivityMainBinding
import com.example.instachat.databinding.ActivityPostBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    lateinit var imagePicLauncher: ActivityResultLauncher<Intent>
    lateinit var selectedImgUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imagePicLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null && data.data != null) {
                    selectedImgUri = data.data!!
                    Glide.with(this).load(selectedImgUri).into(binding.newpostImgView)
                }
            }
        }

        binding.newpostSelectBtn.setOnClickListener {
            ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512, 512).createIntent {
                imagePicLauncher.launch(it)
            }
        }
        binding.newpostCancelBtn.setOnClickListener { onBackPressed() }
        binding.newpostBackbtn.setOnClickListener{ onBackPressed() }

        binding.newpostPostBtn.setOnClickListener {
            FirebaseStorage.getInstance().getReference().child("posts").child(FirebaseUtil.currentUserUid()).listAll().addOnSuccessListener {
                val itemCount = it.items.size
                FirebaseStorage.getInstance().getReference().child("posts").child(FirebaseUtil.currentUserUid()).child(itemCount.toString()).putFile(selectedImgUri).addOnSuccessListener {
                    FirebaseStorage.getInstance().getReference("posts/"+FirebaseUtil.currentUserUid()+"/"+itemCount.toString()).downloadUrl.addOnSuccessListener { uri ->
                        val post=PostModel(uri.toString(),binding.newpostCaption.text.toString(),FirebaseUtil.currentUserUid())
                        Firebase.firestore.collection("posts").add(post)
                        Firebase.firestore.collection("users").document(FirebaseUtil.currentUserUid()).get().addOnSuccessListener {
                            val user = it.toObject<UserModel>()
                            Log.d("testing", user?.postCount.toString())
                            Firebase.firestore.collection("users").document(FirebaseUtil.currentUserUid()).update("postCount", user?.postCount?.plus(1))
                            Log.d("testing", user?.postCount?.plus(1).toString())
                        }
                        finish()
                    }
                }
            }
        }
    }
}