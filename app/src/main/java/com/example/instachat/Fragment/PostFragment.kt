package com.example.instachat.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instachat.Post.PostActivity
import com.example.instachat.Post.ReelsActivity
import com.example.instachat.R
import com.example.instachat.databinding.FragmentPostBinding
import com.example.instachat.databinding.FragmentProfileBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PostFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater, container, false)

        return binding.root
    }
}