package com.erdemklync.shopin.presentation.auth

data class AuthDataState(
    val isLoading: Boolean = false,
    val authState: AuthState = AuthState.None,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val passwordAgain: String = "",
)
