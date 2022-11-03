package com.erdemklync.shopin.presentation.profile

import androidx.lifecycle.ViewModel
import com.erdemklync.shopin.util.Constants.FIELD_FULL_NAME
import com.erdemklync.shopin.util.Constants.FIELD_USERNAME
import com.erdemklync.shopin.util.Constants.USERS
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
): ViewModel() {

    private val _state = MutableStateFlow(ProfileDataState())
    val state get() = _state.asStateFlow()

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        firebaseAuth.currentUser?.let { user ->
            firestore
                .collection(USERS)
                .document(user.uid)
                .get()
                .addOnSuccessListener { snapshot ->
                    _state.update {
                        it.copy(
                            fullName = snapshot.getString(FIELD_FULL_NAME) ?: "",
                            username = snapshot.getString(FIELD_USERNAME) ?: ""
                        )
                    }
                }
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}