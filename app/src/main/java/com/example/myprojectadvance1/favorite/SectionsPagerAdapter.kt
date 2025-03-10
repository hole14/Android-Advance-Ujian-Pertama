package com.example.myprojectadvance1.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myprojectadvance1.ui.FavoriteFragment

class SectionsPagerAdapter internal constructor(activity: FavoriteFragment) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = EventFragment()
        val bundle = Bundle()
        if (position == 0) {
            bundle.putString(EventFragment.ARG_TAB, EventFragment.TAB_NEWS)
        } else {
            bundle.putString(EventFragment.ARG_TAB, EventFragment.TAB_FAVORITE)
        }
        fragment.arguments = bundle
        return fragment
    }
}
