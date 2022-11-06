package com.erdemklync.shopin.presentation.features.sign_up

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemklync.shopin.presentation.features.auth.AuthState
import com.erdemklync.shopin.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpDataState())
    val state get() = _state.asStateFlow()

    private val isRegistrationValid: String?
        get() {
            state.value.let {
                if (it.username.isBlank() || it.email.isBlank() ||
                    it.password.isBlank() || it.passwordAgain.isBlank()) {
                    return "Please fill all fields"
                } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(it.email).matches()) {
                    return "Please enter a valid email address"
                } else if(it.password.length < 6) {
                    return "Password must be at least 6 characters"
                } else if(it.password != it.passwordAgain) {
                    return "Passwords do not match"
                }

                return null
            }
        }

    fun signUp() = viewModelScope.launch {
        state.value.let {
            isRegistrationValid?.let { errorMessage ->
                _state.update {
                    it.copy(
                        authState = AuthState.Error(errorMessage)
                    )
                }
            } ?: kotlin.run {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
                }
                firebaseAuth.createUserWithEmailAndPassword(
                    it.email,
                    it.password,
                ).addOnSuccessListener { result ->
                    result.user?.let { user ->
                        createUserFields(
                            user = user,
                            fullName = it.fullName,
                            username = it.username,
                        )
                    }

                    _state.update {
                        it.copy(
                            authState = AuthState.Success,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun createUserFields(
        user: FirebaseUser,
        fullName: String,
        username: String,
    ) {
        val userData = hashMapOf(
            Constants.FIELD_FULL_NAME to fullName,
            Constants.FIELD_USERNAME to username,
        )

        firestore
            .collection(Constants.USERS)
            .document(user.uid)
            .set(userData)
            .addOnFailureListener {
                Log.e("SignUpViewModel", it.message.toString())
            }
    }

    fun clear() {
        _state.update {
            it.copy(
                username = "",
                email = "",
                password = "",
                passwordAgain = ""
            )
        }
    }

    fun setFullName(fullName: String) {
        _state.update {
            it.copy(
                fullName = fullName
            )
        }
    }

    fun setUsername(username: String) {
        _state.update {
            it.copy(
                username = username
            )
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

    fun setPasswordAgain(password: String) {
        _state.update {
            it.copy(
                passwordAgain = password
            )
        }
    }
}