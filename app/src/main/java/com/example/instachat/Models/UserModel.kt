package com.example.instachat.Models

data class UserModel(
    var userUid: String? ="",
    var profileimage: String="",
    var username:String="",
    var email: String="",
    var password:String="",
    var postCount:Int=0,
    var followersCount:Int=0,
    var followingCount:Int=0,
    var bio:String="",
)
