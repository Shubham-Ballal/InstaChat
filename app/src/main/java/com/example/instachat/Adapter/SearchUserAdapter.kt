package com.example.instachat.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.instachat.Models.ReelModel
import com.example.instachat.Models.UserModel
import com.example.instachat.databinding.ReelDgBinding
import com.example.instachat.databinding.SearchUserRvBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class SearchUserAdapter(private var context: Context,private var userList: ArrayList<UserModel>,private val profileClickListener: ProfileClickListener): RecyclerView.Adapter<SearchUserAdapter.ViewHolder>(){
    interface ProfileClickListener {
        fun onProfileClick(userUid: String)
    }
    class ViewHolder(var binding: SearchUserRvBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = SearchUserRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Firebase.firestore.collection("users").document(userList.get(position).userUid.toString()).get().addOnSuccessListener {
            Glide.with(context).load(it.getString("profileimage")).apply(RequestOptions.circleCropTransform()).into(holder.binding.img)
            holder.binding.username.setText(it.getString("username"))
            holder.binding.layout.setOnClickListener {
                profileClickListener.onProfileClick(userList[position].userUid.toString())
            }
        }
    }
}
