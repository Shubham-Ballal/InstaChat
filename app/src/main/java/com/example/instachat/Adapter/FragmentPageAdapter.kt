package com.example.instachat.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instachat.Fragment.MypostFragment
import com.example.instachat.Fragment.MyreelsFragment
import com.example.instachat.Fragment.ProfileFragment.Companion.userUid

class FragmentPageAdapter(fm:Fragment):FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if(position==0) MypostFragment(userUid)
        else MyreelsFragment(userUid)
    }
}