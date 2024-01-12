package com.example.instachat.Utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import com.example.instachat.Models.PostModel
import com.example.instachat.Models.UserModel
import com.google.firebase.Timestamp

class AndroidUtils {
    companion object{
        fun passUserModelAsIntend(intent: Intent,userModel: UserModel){
            intent.putExtra("profileimage",userModel.profileimage)
            intent.putExtra("username",userModel.username)
            intent.putExtra("email",userModel.email)
            intent.putExtra("password",userModel.password)
        }
        fun passPostModelAsIntend(intent: Intent,postModel: PostModel){
            intent.putExtra("postUri",postModel.postUri)
            intent.putExtra("caption",postModel.caption)
            intent.putExtra("userUid",postModel.userUid)
            intent.putExtra("like",postModel.like)
            intent.putExtra("likeCount",postModel.likeCount)
            intent.putExtra("time",postModel.time)
        }
    }
}