package com.erdemklync.shopin.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class SplashViewState {
    object Loading: SplashViewState()
    object ToMainFragment: SplashViewState()
}

class SplashViewModel: ViewModel() {

    private val _viewState = MutableStateFlow<SplashViewState>(SplashViewState.Loading)
    val viewState get() = _viewState.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            _viewState.value = SplashViewState.ToMainFragment
        }
    }
}