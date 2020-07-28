package com.franjo.github.presentation.features.repository_details

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RepositoryDetailsAdapter(fragment: Fragment, private val tabTitles: Array<String>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = tabTitles.size

    override fun createFragment(position: Int): Fragment {

        // Return a NEW fragment instance in createFragment(int)
        return when (position) {
            0 -> AboutFragment()
            1 -> ReleasesFragment()
            2 -> ContributorsFragment()
            else -> throw IllegalArgumentException()
        }
    }
}
