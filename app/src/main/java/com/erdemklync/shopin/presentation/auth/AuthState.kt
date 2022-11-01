package com.erdemklync.shopin.presentation.auth

sealed class AuthState {
    object None: AuthState()
    object Success: AuthState()
    data class Error(val error: String): AuthState()
}
