package com.example.instachat.Fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instachat.Adapter.ReelAdapter
import com.example.instachat.Models.PostModel
import com.example.instachat.Models.ReelModel
import com.example.instachat.R
import com.example.instachat.Utils.FirebaseUtil
import com.example.instachat.databinding.FragmentMyreelsBinding
import com.example.instachat.databinding.FragmentReelsBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage

class ReelsFragment : Fragment() {
    private lateinit var binding: FragmentReelsBinding
    private lateinit var adapter: ReelAdapter
    var reelList = ArrayList<ReelModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReelsBinding.inflate(inflater, container, false)

        adapter= ReelAdapter(requireContext(),reelList)
//        binding.viewPager.layoutManager= LinearLayoutManager(requireContext())
        binding.viewPager.adapter=adapter
        Firebase.firestore.collection("posts").get().addOnSuccessListener {
            var tempList= arrayListOf<ReelModel>()
            reelList.clear()
            for(i in it.documents){
                var reel=i.toObject<ReelModel>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
            reelList.reverse()
            adapter.notifyDataSetChanged()
        }


        return binding.root
    }
}