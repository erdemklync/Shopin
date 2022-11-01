package com.erdemklync.shopin.presentation.sign_in

import com.erdemklync.shopin.presentation.auth.AuthState

data class SignInDataState(
    val isLoading: Boolean = false,
    val authState: AuthState = AuthState.None,
    val email: String = "",
    val password: String = "",
)
