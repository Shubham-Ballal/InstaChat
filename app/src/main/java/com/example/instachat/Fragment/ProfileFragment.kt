package com.example.instachat.Fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.instachat.Adapter.ViewPagerAdapter
import com.example.instachat.LoginActivity
import com.example.instachat.Models.PostModel
import com.example.instachat.Models.UserModel
import com.example.instachat.Utils.FirebaseUtil
import com.example.instachat.databinding.FragmentProfileBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage

class ProfileFragment : Fragment() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var binding: FragmentProfileBinding
        private lateinit var viewpagerAdapter: ViewPagerAdapter
        private var savedState: Bundle? = null
        lateinit var userUid:String
        lateinit var imagePicLauncher: ActivityResultLauncher<Intent>
        lateinit var selectedImgUri: Uri
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        imagePicLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null && data.data != null) {
                    selectedImgUri = data.data!!
                    Glide.with(this).load(selectedImgUri).apply(RequestOptions.circleCropTransform()).into(binding.profileFragmentImageView)
                    FirebaseStorage.getInstance().getReference().child("profile_picture").child(FirebaseUtil.currentUserUid()).putFile(selectedImgUri).addOnSuccessListener {
                        Firebase.firestore.collection("users").document(FirebaseUtil.currentUserUid()).update("profileimage", selectedImgUri)
                    }
                }
            }
        }
        binding.profileFragmentImageView.setOnClickListener {
            ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512, 512).createIntent {
                imagePicLauncher.launch(it)
            }
        }

        userUid = arguments?.getString("userUid")!!
        Firebase.firestore.collection("users").document(userUid).get().addOnSuccessListener {
            val user=it.toObject<UserModel>()
            binding.profileFragmentUsername.text=user?.username
            binding.profileFragmentBio.text=user?.bio
            binding.profileFragmentPostCount.text= user?.postCount.toString()
            binding.profileFragmentFollowerCount.text=user?.followersCount.toString()
            binding.profileFragmentFollowingCount.text=user?.followingCount.toString()
            if(!user?.profileimage.isNullOrEmpty()){
                Glide.with(this).load(user?.profileimage).apply(RequestOptions.circleCropTransform()).into(binding.profileFragmentImageView)
            }
        }
        binding.profileFragmentMessageBtn.setOnClickListener {
            Firebase.auth.signOut()
            val intent=Intent(context,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        Log.d("check123","msg 3 ")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("check123","msg 2 ")
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            savedState = savedInstanceState.getBundle("viewpagerState")
        }
        setUpviewpager()
    }

    private fun setUpviewpager() {
        Log.d("check123","msg 1 ")
        // Set up your viewpager
        viewpagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewpagerAdapter.addfragment(MypostFragment(userUid), "My Post")
        viewpagerAdapter.addfragment(MyreelsFragment(userUid), "My Reels")
        binding.viewpager?.adapter = viewpagerAdapter

        binding.profileFragmentTabLayout.setupWithViewPager(binding.viewpager)
    }
}