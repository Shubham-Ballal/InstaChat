package com.example.instachat.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.instachat.Fragment.HomeFragment
import com.example.instachat.Fragment.ProfileFragment
import com.example.instachat.MainActivity
import com.example.instachat.MainActivity.Companion.profileFragment
import com.example.instachat.Models.PostModel
import com.example.instachat.R
import com.example.instachat.databinding.PostRvBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class PostAdapter(private var context: Context, private var postList: ArrayList<PostModel>,private val profileClickListener: ProfileClickListener): RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    interface ProfileClickListener {
        fun onProfileClick(userUid: String)
    }
    class ViewHolder(var binding: PostRvBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = PostRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Firebase.firestore.collection("users").document(postList.get(position).userUid).get().addOnSuccessListener {
            if(!it.getString("profileimage").equals(""))
                Glide.with(context).load(it.getString("profileimage")).apply(RequestOptions.circleCropTransform()).into(holder.binding.ProfilePic)
            holder.binding.username.setText(it.getString("username"))
            //var like: Boolean=false,
        }
        Log.d("test1","post uri "+postList.get(position).postUri)
        Glide.with(context).load(postList.get(position).postUri).into(holder.binding.post)
        holder.binding.likeCount.setText(postList.get(position).likeCount.toString()+" likes")
        holder.binding.captions.setText(postList.get(position).caption)
        holder.binding.timepast.setText(postList.get(position).time.toDate().toString())
        holder.binding.username.setOnClickListener {
            profileClickListener.onProfileClick(postList[position].userUid)
        }
        holder.binding.shareimg.setOnClickListener {
            var intent=Intent(Intent.ACTION_SEND)
            intent.type="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,postList.get(position).postUri)
            context.startActivity(intent)
        }
    }
}