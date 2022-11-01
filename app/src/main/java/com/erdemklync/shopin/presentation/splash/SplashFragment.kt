package com.erdemklync.shopin.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.erdemklync.shopin.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment: Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collectLatest { viewState ->
                    when(viewState) {
                        SplashViewEvent.Loading -> {}
                        SplashViewEvent.ToOnBoardingFragment -> {
                            val action = SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment()
                            findNavController().navigate(action)
                        }
                        SplashViewEvent.ToMainFragment -> {
                            val action = SplashFragmentDirections.actionSplashFragmentToProductsFragment()
                            findNavController().navigate(action)
                        }

                        SplashViewEvent.ToAuthFragment -> {
                            val action = SplashFragmentDirections.actionSplashFragmentToAuthFragment()
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }
    }
}