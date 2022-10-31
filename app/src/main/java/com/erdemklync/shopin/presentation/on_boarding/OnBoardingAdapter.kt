package com.erdemklync.shopin.presentation.on_boarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.erdemklync.shopin.R

private val onBoardingItems = listOf(
    R.layout.layout_on_boarding_1,
    R.layout.layout_on_boarding_2,
    R.layout.layout_on_boarding_3,
)

class OnBoardingAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = onBoardingItems.size

    override fun createFragment(position: Int): Fragment {
        return OnBoardingLayout.newInstance(onBoardingItems[position])
    }

}