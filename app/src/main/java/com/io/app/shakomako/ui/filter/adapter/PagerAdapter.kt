package com.io.app.shakomako.ui.filter.adapter

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class PagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentList= ArrayList<Fragment>()
    private var titlesList= ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        Log.e("GetItem","${fragmentList.size}")
        return fragmentList[position]
    }


    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? =titlesList[position]

    fun addFragment(fragment: Fragment){
        fragmentList.add(fragment)
    }
    fun addTitle(title:String){
        titlesList.add(title)
    }






}