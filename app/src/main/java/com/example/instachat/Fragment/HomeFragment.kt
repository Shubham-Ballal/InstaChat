package com.example.instachat.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instachat.Adapter.PostAdapter
import com.example.instachat.Adapter.ReelAdapter
import com.example.instachat.Models.PostModel
import com.example.instachat.Models.ReelModel
import com.example.instachat.R
import com.example.instachat.Utils.FirebaseUtil
import com.example.instachat.databinding.FragmentHomeBinding
import com.example.instachat.databinding.FragmentPostBinding
import com.example.instachat.databinding.FragmentReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class HomeFragment : Fragment(),PostAdapter.ProfileClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: PostAdapter
    var postList = ArrayList<PostModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        adapter = PostAdapter(requireContext(), postList, this)
        binding.MainPostRv?.layoutManager = LinearLayoutManager(requireContext())
        binding.MainPostRv?.adapter = adapter
        Firebase.firestore.collection("posts").get().addOnSuccessListener {
            var tempList = arrayListOf<PostModel>()
            postList.clear()
            for (i in it.documents) {
                var post = i.toObject<PostModel>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            postList.reverse()
            adapter.notifyDataSetChanged()
        }
        binding.img?.setOnClickListener {
            onProfileClick(FirebaseUtil.currentUserUid().toString())
        }

        return binding.root
    }

    override fun onProfileClick(userUid: String) {
        // Create the bundle and set data
        val bundle = Bundle()
        bundle.putString("userUid", userUid) // Replace "key" with your actual key and "value" with the data you want to pass

        // Create the destination fragment
        val destinationFragment = ProfileFragment()
        destinationFragment.arguments = bundle // Attach the bundle to the fragment

        // Navigate to the destination fragment
        parentFragmentManager.beginTransaction().replace(R.id.main_frameLayout, destinationFragment) // Replace "R.id.container" with your actual container ID
            .addToBackStack(null) // Optional: Add to back stack if needed
            .commit()
    }
}

        // Handle profile click event here
        // For example, switch to the profile fragment
//        val profileFragment = ProfileFragment()
//        // pass the user ID or any necessary data to the ProfileFragment if needed
//        // You can replace R.id.main_frameLayout with your actual container ID
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.main_frameLayout, profileFragment)
//            .addToBackStack(null)
//            .commit()
