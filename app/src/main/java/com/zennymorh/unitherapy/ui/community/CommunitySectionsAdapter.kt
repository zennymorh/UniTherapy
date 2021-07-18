package com.zennymorh.unitherapy.ui.community

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zennymorh.unitherapy.ui.community.list.CommunitiesFragment
import com.zennymorh.unitherapy.ui.community.list.FavouriteFragment

class CommunitySectionsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CommunitiesFragment()
            1 -> FavouriteFragment()
            else -> throw IllegalArgumentException("Invalid Fragment Index $position")
        }
    }
}