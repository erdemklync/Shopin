package com.erdemklync.shopin.presentation.features.cart

import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.erdemklync.shopin.R
import com.erdemklync.shopin.databinding.FragmentCartBinding
import com.erdemklync.shopin.domain.model.Product
import com.erdemklync.shopin.presentation.util.setPrice
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BottomSheetDialogFragment() {

    private val viewModel: CartViewModel by viewModels()
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val cartAdapter = CartAdapter(
        onIncrease = { cartItem ->
            viewModel.increaseAmount(cartItem)
        },
        onDecrease = { cartItem ->
            viewModel.decreaseAmount(
                cartItem = cartItem,
                showRemoveFromCartDialog = { productToBeRemoved ->
                    showRemoveFromCartDialog(productToBeRemoved)
                }
            )
        }
    )
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.apply {
                backgroundTintList = ColorStateList.valueOf(ResourcesCompat.getColor(resources, R.color.white, context.theme))
                val behaviour = BottomSheetBehavior.from(this)
                setupFullHeight(this)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            recyclerViewCart.adapter = cartAdapter
            recyclerViewCart.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            buttonCheckout.setOnClickListener {
                showConfirmPaymentDialog()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    cartAdapter.submitList(state.cartItems)
                    binding.textPriceTotal setPrice state.total
                    binding.textEmptyCart.isVisible = state.cartItems.isEmpty()
                    binding.groupCart.isVisible = state.cartItems.isNotEmpty()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showRemoveFromCartDialog(product: Product) = MaterialAlertDialogBuilder(requireContext())
        .setTitle(resources.getString(R.string.dialog_title_remove_from_cart))
        .setMessage(resources.getString(R.string.dialog_message_remove_from_cart))
        .setPositiveButton(resources.getString(R.string.remove)){ dialog, _ ->
            dialog.dismiss()
            viewModel.removeProductFromCart(product)
        }
        .setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }
        .show()

    private fun showConfirmPaymentDialog() = MaterialAlertDialogBuilder(requireContext())

        .setTitle(resources.getString(R.string.dialog_title_confirm_payment))
        .setMessage(resources.getString(R.string.dialog_message_confirm_payment))
        .setPositiveButton(resources.getString(R.string.confirm)){ dialog, _ ->
            dialog.dismiss()
            viewModel.clearCart()
            showPaymentSuccessfulDialog()
        }
        .setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }
        .show()

    private fun showPaymentSuccessfulDialog() = MaterialAlertDialogBuilder(requireContext())
        .setIcon(R.drawable.ic_check)
        .setMessage(resources.getString(R.string.dialog_message_payment_successful))
        .setCancelable(false)
        .show()
        .also {
            lifecycleScope.launch {
                delay(2000)
                findNavController().popBackStack()
                it.cancel()
            }
        }
}