package com.example.instachat.Models

import com.google.firebase.Timestamp

data class ReelModel(
    var reelUri:String="",
    var caption:String="",
    var userUid: String="",
    var like: Boolean=false,
    var likeCount: Int=0,
    var time: Timestamp=Timestamp.now()
)
