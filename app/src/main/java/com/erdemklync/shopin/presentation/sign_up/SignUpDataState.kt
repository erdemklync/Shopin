package com.erdemklync.shopin.presentation.sign_up

import com.erdemklync.shopin.presentation.auth.AuthState

data class SignUpDataState(
    val isLoading: Boolean = false,
    val authState: AuthState = AuthState.None,
    val fullName: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val passwordAgain: String = "",
)
