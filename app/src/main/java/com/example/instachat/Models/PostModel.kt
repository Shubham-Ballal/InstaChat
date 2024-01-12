package com.example.instachat.Models

import android.net.Uri
import com.google.firebase.Timestamp

data class PostModel(
    var postUri:String="",
    var caption:String="",
    var userUid: String="",
    var like: Boolean=false,
    var likeCount: Int=0,
    var time:Timestamp=Timestamp.now()
)
