package com.erdemklync.shopin.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.erdemklync.shopin.databinding.FragmentCartBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CartFragment : BottomSheetDialogFragment() {

    private val viewModel: CartViewModel by viewModels()
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFullHeight()
    }

    private fun setupFullHeight() {
        val bottomSheetDialog = dialog as BottomSheetDialog
        val behavior = bottomSheetDialog.behavior
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}