package com.erdemklync.shopin.presentation.features.auth

sealed class AuthState {
    object None: AuthState()
    object Success: AuthState()
    data class Error(val error: String): AuthState()
}
