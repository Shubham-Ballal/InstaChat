package com.example.instachat.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instachat.Models.PostModel
import com.example.instachat.R
import com.example.instachat.ShowPostActivity
import com.example.instachat.Utils.AndroidUtils
import com.example.instachat.databinding.MypostRvDesignBinding

class MyPostRvAdapter(private val context: Context, private val dataList: ArrayList<PostModel>): RecyclerView.Adapter<MyPostRvAdapter.ViewHolder>() {
    class ViewHolder(val binding: MypostRvDesignBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MypostRvDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    //    Glide.with(context).load(dataList[position].postUri).into(holder.binding.mypostRvImgView)
        Glide.with(context)
            .load(dataList[position].postUri)
            .placeholder(R.drawable.image_icon) // Placeholder image
            .error(R.drawable.image_icon) // Error image
            .into(holder.binding.mypostRvImgView)
        holder.binding.mypostRvImgView.setOnClickListener {
            val intent=Intent(context,ShowPostActivity::class.java)
            intent.putExtra("postUri",dataList[position].postUri)
            context.startActivity(intent)
        }
    }
}