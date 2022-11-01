package com.erdemklync.shopin.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemklync.shopin.data.local.DataStoreManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val firebaseAuth: FirebaseAuth,
): ViewModel() {

    private val _viewState = MutableStateFlow<SplashViewEvent>(SplashViewEvent.Loading)
    val viewState get() = _viewState.asSharedFlow()

    private val userSignedIn: Boolean
        get() = firebaseAuth.currentUser != null

    init {
        viewModelScope.launch {
            dataStoreManager.getFirstTime.collectLatest { isFirstTime ->
                if (userSignedIn) {
                    _viewState.value = SplashViewEvent.ToMainFragment
                } else {
                    if(isFirstTime) {
                        _viewState.value = SplashViewEvent.ToOnBoardingFragment
                    } else {
                        _viewState.value = SplashViewEvent.ToAuthFragment
                    }
                }
            }
        }
    }
}