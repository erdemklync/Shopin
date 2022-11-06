package com.erdemklync.shopin.presentation.features.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.erdemklync.shopin.R
import com.erdemklync.shopin.databinding.FragmentProductDetailBinding
import com.erdemklync.shopin.presentation.util.setPrice
import com.erdemklync.shopin.presentation.util.setProductImage
import com.erdemklync.shopin.presentation.util.setProductRating
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by viewModels()

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    with(binding) {
                        state.product?.let { product ->
                            imageProduct setProductImage product.image
                            textProductRating setProductRating product.rating
                            textProductPrice setPrice product.price
                            ratingBarProduct.rating = product.rating.rate.toFloat()
                            textProductTitle.text = product.title
                            textProductDescription.text = product.description
                            textProductCategory.text = product.category
                        }

                        loadingIndicator.isVisible = state.isLoading
                        groupProductDetail.isVisible = !state.isLoading

                        viewAddToCart.amount = state.amount

                        viewAddToCart.buttonIncrease.setOnClickListener {
                            viewModel.incrementAmount()
                        }

                        viewAddToCart.buttonDecrease.setOnClickListener {
                            viewModel.decrementAmount()
                        }

                        viewAddToCart.buttonAddToCart.setOnClickListener {
                            viewModel.addToCart(
                                onSuccess = {
                                    Toast.makeText(requireContext(), resources.getString(R.string.toast_message_added_to_cart), Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                }
            }
        }

        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}