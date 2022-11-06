package com.erdemklync.shopin.presentation.features.sign_in

import androidx.lifecycle.ViewModel
import com.erdemklync.shopin.presentation.features.auth.AuthState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {

    private val _state = MutableStateFlow(SignInDataState())
    val state get() = _state.asStateFlow()

    private var auth: FirebaseAuth = Firebase.auth

    fun signIn() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        auth.signInWithEmailAndPassword(
            state.value.email,
            state.value.password,
        ).addOnSuccessListener {
            _state.update {
                it.copy(
                    authState = AuthState.Success,
                    isLoading = false
                )
            }
        }.addOnFailureListener { exception ->
            _state.update {
                it.copy(
                    authState = AuthState.Error(exception.message ?: ""),
                    isLoading = false
                )
            }
        }
    }

    fun setEmail(email: String) {
        _state.update {
            it.copy(
                email = email
            )
        }
    }

    fun setPassword(password: String) {
        _state.update {
            it.copy(
                password = password
            )
        }
    }
}