package com.erdemklync.shopin.presentation.features.auth

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.erdemklync.shopin.presentation.features.sign_in.SignInFragment
import com.erdemklync.shopin.presentation.features.sign_up.SignUpFragment

private val authFragments = listOf(
    SignInFragment(),
    SignUpFragment()
)

class AuthAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = authFragments.size
    override fun createFragment(position: Int): Fragment = authFragments[position]
}