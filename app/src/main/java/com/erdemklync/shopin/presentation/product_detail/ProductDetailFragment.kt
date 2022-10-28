package com.erdemklync.shopin.presentation.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.erdemklync.shopin.databinding.FragmentProductDetailBinding
import com.erdemklync.shopin.util.DataState
import com.erdemklync.shopin.util.setProductImage
import com.erdemklync.shopin.util.setProductPrice
import com.erdemklync.shopin.util.setProductRating
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
                    when(val dataState = state.dataState) {
                        is DataState.Loading -> {}
                        is DataState.Error -> {
                            Toast.makeText(requireContext(), dataState.message, Toast.LENGTH_LONG).show()
                        }
                        is DataState.Success -> {
                            with(binding) {
                                dataState.data.let { product ->
                                    imageProduct setProductImage product.image
                                    textProductRating setProductRating product.rating
                                    textProductPrice setProductPrice product.price
                                    textProductTitle.text = product.title
                                    textProductDescription.text = product.description
                                    textProductCategory.text = product.category
                                }

                                textAmount.text = state.amount.toString()

                                loadingIndicator.visibility =  View.GONE
                                contentDetail.visibility =  View.VISIBLE
                                layoutBottom.visibility = View.VISIBLE

                                buttonDecrement.setOnClickListener {
                                    viewModel.decrementAmount()
                                }

                                buttonIncrement.setOnClickListener {
                                    viewModel.incrementAmount()
                                }

                                buttonAddToCart.setOnClickListener {
                                    viewModel.addToCart()
                                }
                            }
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