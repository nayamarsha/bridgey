package com.example.bridgey2.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.bridgey2.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ScheduleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
//        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
//
////        val adapter = TabPagerAdapter // pakai this, bukan requireActivity()
////        viewPager.adapter = adapter
////
////        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
////            tab.text = when (position) {
////                0 -> "Event"
////                1 -> "Sponsor"
////                2 -> "Tent"
////                else -> ""
////            }
////        }.attach()
//    }
}