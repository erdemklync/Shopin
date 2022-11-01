package com.erdemklync.shopin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.erdemklync.shopin.databinding.ActivityMainBinding
import com.google.android.material.elevation.SurfaceColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.isVisible = destination.id !in fragmentsWithoutNavigation
            binding.toolbar.isVisible = destination.id !in fragmentsWithoutNavigation

            setSystemBarsColor(destination.id)
        }
    }

    private val fragmentsWithoutNavigation = listOf(
        R.id.splashFragment,
        R.id.onBoardingFragment,
        R.id.authFragment,
        R.id.productDetailFragment,
    )

    private fun setSystemBarsColor(destinationId: Int) {
        when(destinationId) {
            R.id.splashFragment, R.id.onBoardingFragment -> {}
            else -> {
                val surfaceColorAtElevation = SurfaceColors.SURFACE_2.getColor(this)
                val surfaceColor = SurfaceColors.SURFACE_0.getColor(this)

                window.navigationBarColor = surfaceColorAtElevation
                window.statusBarColor = surfaceColor
            }
        }
    }
}