package com.erdemklync.shopin.presentation.sign_up

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.erdemklync.shopin.R
import com.erdemklync.shopin.databinding.FragmentSignUpBinding
import com.erdemklync.shopin.presentation.auth.AuthProgressDialog
import com.erdemklync.shopin.presentation.auth.AuthState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class SignUpFragment: Fragment(R.layout.fragment_sign_up) {

    private val viewModel: SignUpViewModel by viewModels()

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loadingDialog = AuthProgressDialog(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state.isLoading) {
                        loadingDialog.show()
                    } else {
                        loadingDialog.hide()
                    }

                    when(val uiState = state.authState) {
                        AuthState.Success -> {
                            AlertDialog
                                .Builder(requireContext())
                                .setIcon(R.drawable.ic_check)
                                .setTitle("Kayıt başarılı!")
                                .setMessage("Giriş sayfasına yönlendiriliyorsunuz.")
                                .show()
                        }
                        is AuthState.Error -> {
                            Snackbar.make(binding.root, uiState.error, Snackbar.LENGTH_LONG).show()
                        }
                        else -> {}
                    }
                }
            }
        }

        binding.editTextSignUpUsername.doAfterTextChanged {
            viewModel.setUsername(it.toString())
        }

        binding.editTextSignUpEmail.doAfterTextChanged {
            viewModel.setEmail(it.toString())
        }

        binding.editTextSignUpPassword.doAfterTextChanged {
            viewModel.setPassword(it.toString())
        }

        binding.editTextSignUpPasswordAgain.doAfterTextChanged {
            viewModel.setPasswordAgain(it.toString())
        }

        binding.buttonSignUp.setOnClickListener {
            viewModel.signUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}