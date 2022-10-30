package com.erdemklync.shopin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.erdemklync.shopin.databinding.ActivityMainBinding
import com.google.android.material.elevation.SurfaceColors
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
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
            binding.bottomNavigationView.visibility = shouldShowBars(destination.id)
            binding.toolbar.visibility = shouldShowBars(destination.id)

            setSystemBarsColor(destination.id)
        }
    }

    private val fragmentsWithoutNavigation = listOf(
        R.id.splashFragment,
        R.id.productDetailFragment,
    )

    private fun shouldShowBars(destinationId: Int): Int {
        if(destinationId in fragmentsWithoutNavigation) {
            return View.GONE
        }
        return View.VISIBLE
    }

    private fun setSystemBarsColor(destinationId: Int) {
        when(destinationId) {
            R.id.splashFragment -> {
                window.navigationBarColor = ContextCompat.getColor(this, R.color.brand_color_primary)
                window.statusBarColor = ContextCompat.getColor(this, R.color.brand_color_primary)
            }
            else -> {
                val surfaceColorAtElevation = SurfaceColors.SURFACE_2.getColor(this)
                val surfaceColor = SurfaceColors.SURFACE_0.getColor(this)

                window.navigationBarColor = surfaceColorAtElevation
                window.statusBarColor = surfaceColor
            }
        }
    }
}