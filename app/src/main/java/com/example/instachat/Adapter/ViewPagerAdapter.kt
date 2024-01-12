package com.example.instachat.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm:FragmentManager): FragmentStatePagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    val fragmentList= mutableListOf<Fragment>()
    val titleList= mutableListOf<String>()

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }
    override fun getPageTitle(position: Int): CharSequence {
        return titleList.get(position)
    }
    fun addfragment(fragment:Fragment,title:String){
        fragmentList.add(fragment)
        titleList.add(title)
    //    notifyDataSetChanged()
    }
}