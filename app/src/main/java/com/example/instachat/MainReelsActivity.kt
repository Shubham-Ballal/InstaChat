package com.example.instachat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.instachat.Adapter.ReelAdapter
import com.example.instachat.Models.ReelModel
import com.example.instachat.databinding.ActivityMainReelsBinding
import com.example.instachat.databinding.ActivityReelsBinding
import com.example.instachat.databinding.ReelDgBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MainReelsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainReelsBinding
    private lateinit var adapter: ReelAdapter
    var reelList = ArrayList<ReelModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainReelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter= ReelAdapter(this,reelList)
        binding.viewPager.adapter=adapter
        Firebase.firestore.collection("reels").get().addOnSuccessListener {
            var tempList= arrayListOf<ReelModel>()
            reelList.clear()
            for(i in it.documents){
                var reel=i.toObject<ReelModel>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
            reelList.reverse()
            adapter.notifyDataSetChanged()
        }
    }
}