package com.erdemklync.shopin.presentation.features.on_boarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.erdemklync.shopin.R
import com.erdemklync.shopin.databinding.FragmentOnBoardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment: Fragment() {

    private val viewModel: OnBoardingViewModel by viewModels()

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        binding.viewPager.adapter = OnBoardingAdapter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    binding.buttonBack.isVisible = position != 0

                    if (position == 2) {
                        binding.buttonNext.text = resources.getString(R.string.finish)
                    } else {
                        binding.buttonNext.text = resources.getString(R.string.next)
                    }
                }
            }
        )

        binding.buttonSkip.setOnClickListener {
            navigateToProducts()
        }

        binding.buttonBack.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            binding.viewPager.currentItem = currentItem - 1
        }

        binding.buttonNext.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem == 2) {
                navigateToProducts()
            } else {
                binding.viewPager.currentItem = currentItem + 1
            }
        }

    }

    private fun navigateToProducts() {
        val action = OnBoardingFragmentDirections.actionOnBoardingFragmentToAuthFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}