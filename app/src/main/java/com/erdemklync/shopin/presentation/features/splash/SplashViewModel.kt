package com.erdemklync.shopin.presentation.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemklync.shopin.data.local.DataStoreManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val firebaseAuth: FirebaseAuth,
): ViewModel() {

    private val _viewState = MutableSharedFlow<SplashViewEvent>()
    val viewState get() = _viewState.asSharedFlow()

    private val userSignedIn: Boolean
        get() = firebaseAuth.currentUser != null

    init {
        viewModelScope.launch {
            dataStoreManager.getFirstTime.collect { isFirstTime ->
                if (userSignedIn) {
                    delay(4000)
                    _viewState.emit(SplashViewEvent.ToMainFragment)
                } else {
                    if(isFirstTime) {
                        _viewState.emit(SplashViewEvent.ToOnBoardingFragment)
                    } else {
                        _viewState.emit(SplashViewEvent.ToAuthFragment)
                    }
                }
            }
        }
    }
}