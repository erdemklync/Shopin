package com.erdemklync.shopin.presentation.auth

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.erdemklync.shopin.presentation.sign_in.SignInFragment
import com.erdemklync.shopin.presentation.sign_up.SignUpFragment

private val authFragments = listOf(
    SignInFragment(),
    SignUpFragment()
)

class AuthAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = authFragments.size
    override fun createFragment(position: Int): Fragment = authFragments[position]
}