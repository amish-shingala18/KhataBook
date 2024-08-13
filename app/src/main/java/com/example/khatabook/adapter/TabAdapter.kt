package com.example.khatabook.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.khatabook.fragment.AllUserFragment
import com.example.khatabook.fragment.HomeFragment

class TabAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> HomeFragment()
            1-> AllUserFragment()
            else->HomeFragment()
        }
    }
}