package com.example.bridgey2.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bridgey2.Fragments.EventFragment
import com.example.bridgey2.Fragments.SponsorFragment
import com.example.bridgey2.Fragments.TentFragment

class TabPagerAdapter(
    fragmentManager: FragmentManager,
    lifeCycle: Lifecycle

) : FragmentStateAdapter(fragmentManager, lifeCycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EventFragment()
            1 -> SponsorFragment()
            2 -> TentFragment()
            else -> EventFragment()
        }
    }
}
