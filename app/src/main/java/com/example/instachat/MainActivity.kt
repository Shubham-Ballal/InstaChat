package com.example.instachat

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.widget.LinearLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.instachat.Fragment.HomeFragment
import com.example.instachat.Fragment.PostFragment
import com.example.instachat.Fragment.ProfileFragment
import com.example.instachat.Fragment.ReelsFragment
import com.example.instachat.Fragment.SearchFragment
import com.example.instachat.Post.PostActivity
import com.example.instachat.Post.ReelsActivity
import com.example.instachat.R.id.postfragment_post
import com.example.instachat.Utils.FirebaseUtil
import com.example.instachat.databinding.ActivityMainBinding
import com.example.instachat.databinding.FragmentPostBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textview.MaterialTextView

class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var bottomNavigationView:BottomNavigationView
        var homeFragment = HomeFragment()
        var searchFragment = SearchFragment()
        var postFragment = PostFragment()
        var reelsFragment = ReelsFragment()
        var profileFragment = ProfileFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView= findViewById(R.id.bottom_nav)

        bottomNavigationView.setOnItemSelectedListener {
            if (it.itemId == R.id.menu_home){
                supportFragmentManager.beginTransaction().replace(R.id.main_frameLayout, homeFragment).commit()
            }
            if (it.itemId == R.id.menu_search){
                supportFragmentManager.beginTransaction().replace(R.id.main_frameLayout, searchFragment).commit()
            }
            if (it.itemId == R.id.menu_post){
                val dialog = BottomSheetDialog(this)
                val dialogBinding = FragmentPostBinding.inflate(layoutInflater)
                dialog.setContentView(dialogBinding.root)
                dialog.show()

                dialogBinding.postfragmentPost.setOnClickListener {
                    startActivity(Intent(this, PostActivity::class.java))
                    dialog.dismiss()
                }
                dialogBinding.postfragmentReels.setOnClickListener {
                    startActivity(Intent(this, ReelsActivity::class.java))
                    dialog.dismiss()
                }

            //    supportFragmentManager.beginTransaction().replace(R.id.main_frameLayout, postFragment).commit()
            }
            if (it.itemId == R.id.menu_reels){
                startActivity(Intent(this,MainReelsActivity::class.java))
//                supportFragmentManager.beginTransaction().replace(R.id.main_frameLayout, reelsFragment).commit()
            }
            if (it.itemId == R.id.menu_profile){
                val bundle = Bundle()
                bundle.putString("userUid", FirebaseUtil.currentUserUid()) // Replace "key" with your actual key and "value" with the data you want to pass

// Create the destination fragment
                val destinationFragment = ProfileFragment()
                destinationFragment.arguments = bundle // Attach the bundle to the fragment

// Navigate to the destination fragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frameLayout, destinationFragment) // Replace "R.id.container" with your actual container ID
                    .addToBackStack(null) // Optional: Add to back stack if needed
                    .commit()
//                supportFragmentManager.beginTransaction().replace(R.id.main_frameLayout, profileFragment).commit()
            }
            return@setOnItemSelectedListener false
        }
        bottomNavigationView.selectedItemId = R.id.menu_home
    }
}

//FirebaseUtil.currentProfilePicStorageReferance().downloadUrl.addOnCompleteListener {
//    if (it.isSuccessful) {
//        val uri: Uri = it.result
//        Glide.with(this).load(uri).transform(CircleCrop()).into(userImg)
//    }
//}