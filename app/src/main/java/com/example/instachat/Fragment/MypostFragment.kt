package com.example.instachat.Fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instachat.Adapter.MyPostRvAdapter
import com.example.instachat.Models.PostModel
import com.example.instachat.Utils.FirebaseUtil
import com.example.instachat.databinding.FragmentMypostBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage

class MypostFragment(private val userUid: String) : Fragment() {
    private lateinit var binding: FragmentMypostBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMypostBinding.inflate(inflater, container, false)

        val postList = ArrayList<PostModel>()
        val adapter = MyPostRvAdapter(requireContext(), postList)
        binding.mypostRv.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.mypostRv.adapter = adapter

        FirebaseStorage.getInstance().getReference().child("posts").child(userUid).listAll().addOnSuccessListener { listResult ->
            val tasks = mutableListOf<Task<Uri>>()

            for (item in listResult.items) {
                val downloadTask = item.downloadUrl // Get download URL task
                tasks.add(downloadTask)

                downloadTask.addOnSuccessListener { downloadUri ->
                    val fileName = item.name // Get file name
                    val post = PostModel(downloadUri.toString(), "")
                    postList.add(post)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        return binding.root
    }
}
