package com.example.instachat.Utils

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

class FirebaseUtil {
    companion object{
        fun currentUserUid(): String {
            return FirebaseAuth.getInstance().uid.toString()
        }
        fun isLoggedIn(): Boolean{
            if(currentUserUid() =="") return false
            return currentUserUid() != "null"
        }
//        fun currentUserDetails(): DocumentReference {
//            return FirebaseFirestore.getInstance().collection("users").document(currentUserUid())
//        }
//        fun allUserCollectionsReference(): CollectionReference {
//            return FirebaseFirestore.getInstance().collection("users")
//        }
//        fun getChatroomReference(chatroomId: String): DocumentReference {
//            return FirebaseFirestore.getInstance().collection("chatroom").document(chatroomId)
//        }
//        fun getChatroomId(userId1:String,userId2: String): String{
//            if(userId1>userId2) return userId1+userId2
//            else return userId2+userId1
//        }
//        fun getChatroomMessageReferance(chatroomId: String): CollectionReference {
//            return getChatroomReference(chatroomId).collection("chats")
//        }
//        fun allChatroomCollectionReference(): CollectionReference {
//            return FirebaseFirestore.getInstance().collection("chatroom")
//        }
//        fun getOtherUserFromChatroom(userIds: List<String>): DocumentReference {
//            if(userIds[0]==FirebaseUtil.currentUserUid()) return allUserCollectionsReference().document(userIds[1])
//            else return allUserCollectionsReference().document(userIds[0])
//        }
//        fun timestampToHourAndMin(timestamp: Timestamp): String{
//            return SimpleDateFormat("HH:MM").format(timestamp.toDate())
//        }
//        fun logout(){
//            FirebaseAuth.getInstance().signOut()
//        }
//        fun currentProfilePicStorageReferance():StorageReference{
//            return FirebaseStorage.getInstance().getReference().child("profile_pic").child(currentUserUid())
//        }
//        fun otherProfilePicStorageReferance(otherUserId:String):StorageReference{
//            return FirebaseStorage.getInstance().getReference().child("profile_pic").child(otherUserId)
//        }
    }
}