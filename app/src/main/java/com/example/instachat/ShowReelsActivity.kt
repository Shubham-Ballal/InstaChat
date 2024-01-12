package com.example.instachat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.instachat.databinding.ActivityShowPostBinding
import com.example.instachat.databinding.ActivityShowReelsBinding

class ShowReelsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowReelsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityShowReelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reelDgVideoView.setVideoPath(intent.getStringExtra("reelUri"))
        binding.reelDgVideoView.setOnPreparedListener {
            binding.progressBar.visibility= View.GONE
            binding.reelDgVideoView.start()
        }
    }
}