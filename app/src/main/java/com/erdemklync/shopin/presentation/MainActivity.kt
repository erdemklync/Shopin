package com.erdemklync.shopin.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.erdemklync.shopin.R
import com.erdemklync.shopin.databinding.ActivityMainBinding
import com.erdemklync.shopin.presentation.util.setPrice
import com.google.android.material.elevation.SurfaceColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private val insetsController: WindowInsetsControllerCompat? by lazy {
        window?.let {
            window -> WindowInsetsControllerCompat(window, window.decorView)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenResumed {
            viewModel.cartTotal.collect { cartTotal ->
                binding.buttonCart setPrice cartTotal
            }
        }

        initView()

        binding.buttonCart.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.action_global_cartFragment
            )
        }
    }

    private fun initView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.apply {
            bottomNavigationView.setupWithNavController(navController)
            lifecycleScope.launchWhenResumed {
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    when(destination.id) {
                        R.id.splashFragment,
                        R.id.onBoardingFragment, -> {
                            showSystemBars(false)
                            setStatusBarItemsColor(false)
                            window.navigationBarColor = ContextCompat.getColor(this@MainActivity,
                                R.color.md_theme_light_primary
                            )
                            window.statusBarColor = ContextCompat.getColor(this@MainActivity,
                                R.color.md_theme_light_primary
                            )
                        }
                        R.id.authFragment,
                        R.id.productDetailFragment -> {
                            showSystemBars(false)
                            setStatusBarItemsColor(true)
                            window.navigationBarColor = SurfaceColors.SURFACE_0.getColor(this@MainActivity)
                            window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this@MainActivity)
                        }
                        else -> {
                            showSystemBars(true)
                            setStatusBarItemsColor(true)
                            window.navigationBarColor = SurfaceColors.SURFACE_2.getColor(this@MainActivity)
                            window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this@MainActivity)
                        }
                    }
                }
            }
        }
    }

    private fun showSystemBars(visible: Boolean) {
        binding.apply {
            bottomNavigationView.isVisible = visible
            toolbar.isVisible = visible
        }
    }

    private fun setStatusBarItemsColor(darkIcons: Boolean) {
        insetsController?.isAppearanceLightStatusBars = darkIcons
    }
}