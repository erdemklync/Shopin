package com.erdemklync.shopin.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.erdemklync.shopin.databinding.FragmentAuthBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment: Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        setUpViewPager()
        setUpTabLayout()
        return binding.root
    }

    private fun setUpViewPager() {
        binding.viewPager.apply {
            adapter = AuthAdapter(this@AuthFragment)
            isUserInputEnabled = false
        }
    }

    private fun setUpTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Sign In"
                1 -> tab.text = "Sign Up"
            }
        }.attach()
    }

    fun navigateToMain() {
        val action = AuthFragmentDirections.actionAuthFragmentToProductsFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}