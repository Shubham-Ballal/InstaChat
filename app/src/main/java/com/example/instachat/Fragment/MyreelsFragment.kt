package com.example.instachat.Fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instachat.Adapter.MyReelRvAdapter
import com.example.instachat.Models.ReelModel
import com.example.instachat.Utils.FirebaseUtil
import com.example.instachat.databinding.FragmentMyreelsBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage

class MyreelsFragment(private val userUid: String) : Fragment() {
    private lateinit var binding: FragmentMyreelsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMyreelsBinding.inflate(inflater, container, false)

        var reelList= ArrayList<ReelModel>()
        var adapter= MyReelRvAdapter(requireContext(),reelList)
        binding.myreelRv.layoutManager= StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.myreelRv.adapter=adapter

        FirebaseStorage.getInstance().getReference().child("reels").child(userUid)
            .listAll().addOnSuccessListener { listResult ->
                val tasks = mutableListOf<Task<Uri>>()

                for (item in listResult.items) {
                    val downloadTask = item.downloadUrl // Get download URL task
                    tasks.add(downloadTask)

                    downloadTask.addOnSuccessListener { downloadUri ->
                        val fileName = item.name // Get file name
                        val reel = ReelModel(downloadUri.toString(), "")
                        reelList.add(reel)

                        adapter.notifyDataSetChanged()
                    }
                }
            }
        return binding.root
    }
}
