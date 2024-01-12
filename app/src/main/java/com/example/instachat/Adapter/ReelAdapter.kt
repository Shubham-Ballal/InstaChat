package com.example.instachat.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.instachat.Models.ReelModel
import com.example.instachat.databinding.ReelDgBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ReelAdapter(private var context: Context,private var reelList: ArrayList<ReelModel>):RecyclerView.Adapter<ReelAdapter.ViewHolder>() {
    class ViewHolder(var binding: ReelDgBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ReelDgBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Firebase.firestore.collection("users").document(reelList.get(position).userUid).get().addOnSuccessListener {
            Glide.with(context).load(it.getString("profileimage")).apply(RequestOptions.circleCropTransform()).into(holder.binding.reelDgImgView)
            holder.binding.reelDgTextView.setText(it.getString("username"))
        }
        holder.binding.reelDgTextView2.setText(reelList.get(position).caption)
        holder.binding.reelDgVideoView.setVideoPath(reelList.get(position).reelUri)
        holder.binding.reelDgVideoView.setOnPreparedListener {
            holder.binding.progressBar.visibility=View.GONE
            holder.binding.reelDgVideoView.start()
        }
    }
}