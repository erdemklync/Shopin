package com.erdemklync.shopin.presentation.sign_in

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
import com.erdemklync.shopin.databinding.FragmentSignInBinding
import com.erdemklync.shopin.presentation.auth.AuthFragment
import com.erdemklync.shopin.presentation.auth.AuthProgressDialog
import com.erdemklync.shopin.presentation.auth.AuthState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class SignInFragment: Fragment() {

    private val viewModel: SignInViewModel by viewModels()

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
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

                    when(state.authState) {
                        AuthState.Success -> {
                            (parentFragment as AuthFragment).navigateToMain()
                        }
                        is AuthState.Error -> {
                            Snackbar.make(binding.root, state.authState.error, Snackbar.LENGTH_LONG).show()
                        }
                        else -> {}
                    }
                }
            }
        }

        binding.editTextSignInEmail.doAfterTextChanged {
            viewModel.setEmail(it.toString())
        }

        binding.editTextSignInPassword.doAfterTextChanged {
            viewModel.setPassword(it.toString())
        }

        binding.buttonSignIn.setOnClickListener {
            viewModel.signIn()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}