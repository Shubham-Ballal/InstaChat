package com.example.instachat.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instachat.Adapter.PostAdapter
import com.example.instachat.Adapter.SearchUserAdapter
import com.example.instachat.Models.PostModel
import com.example.instachat.Models.UserModel
import com.example.instachat.R
import com.example.instachat.databinding.FragmentHomeBinding
import com.example.instachat.databinding.FragmentSearchBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class SearchFragment : Fragment(), SearchUserAdapter.ProfileClickListener {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchUserAdapter
    var userList=ArrayList<UserModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)


        binding.searchBtn.setOnClickListener {
            val searchTerm: String = binding.searchBox.text.toString()
            if(searchTerm.length<1){
                binding.searchBox.error = "Invalid Username"
                return@setOnClickListener
            }
            setUpSearchRecyclerView(searchTerm)
        }


        return binding.root
    }

    private fun setUpSearchRecyclerView(searchTerm: String) {
        adapter = SearchUserAdapter(requireContext(),userList,this)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter=adapter

        FirebaseFirestore.getInstance().collection("users").whereGreaterThanOrEqualTo("username",searchTerm).get().addOnSuccessListener{
            if(!it.isEmpty){
                var tempList= arrayListOf<UserModel>()
                userList.clear()
                for(data in it.documents){
                    var user=data.toObject<UserModel>()!!
                    tempList.add(user)
//                    val user: UserModel? = data.toObject(UserModel::class.java)
//                    userList.add(user!!)
                }
                userList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onProfileClick(userUid: String) {
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