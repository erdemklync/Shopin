package com.erdemklync.shopin.presentation.features.splash

sealed class SplashViewEvent {
    object Loading: SplashViewEvent()
    object ToMainFragment: SplashViewEvent()
    object ToOnBoardingFragment: SplashViewEvent()
    object ToAuthFragment: SplashViewEvent()
}