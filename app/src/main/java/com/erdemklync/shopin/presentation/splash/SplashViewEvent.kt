package com.erdemklync.shopin.presentation.splash

sealed class SplashViewEvent {
    object Loading: SplashViewEvent()
    object ToMainFragment: SplashViewEvent()
    object ToOnBoardingFragment: SplashViewEvent()
}