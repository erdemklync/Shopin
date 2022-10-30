package com.erdemklync.shopin.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemklync.shopin.data.local.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
): ViewModel() {

    private val _viewState = MutableStateFlow<SplashViewEvent>(SplashViewEvent.Loading)
    val viewState get() = _viewState.asSharedFlow()

    init {
        viewModelScope.launch {
            dataStoreManager.getFirstTime.collectLatest { isFirstTime ->
                _viewState.value = if(isFirstTime) {
                    SplashViewEvent.ToOnBoardingFragment
                } else {
                    SplashViewEvent.ToMainFragment
                }
            }
        }
    }
}