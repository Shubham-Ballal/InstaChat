package com.example.instachat.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instachat.Models.ReelModel
import com.example.instachat.R
import com.example.instachat.ShowPostActivity
import com.example.instachat.ShowReelsActivity
import com.example.instachat.databinding.MyreelRvDesignBinding

class MyReelRvAdapter(private val context: Context,private val dataList: ArrayList<ReelModel>):RecyclerView.Adapter<MyReelRvAdapter.ViewHolder>() {

    class ViewHolder(val binding: MyreelRvDesignBinding):RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=MyreelRvDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    //    Glide.with(context).load(dataList[position].reelUri).into(holder.binding.myreelRvDesign)
        Glide.with(context)
            .load(dataList[position].reelUri)
            .placeholder(R.drawable.image_icon) // Placeholder image
            .error(R.drawable.image_icon) // Error image
            .into(holder.binding.myreelRvDesign)
        holder.binding.myreelRvDesign.setOnClickListener {
            val intent= Intent(context, ShowReelsActivity::class.java)
            intent.putExtra("reelUri",dataList[position].reelUri)
            context.startActivity(intent)
        }
    }
}