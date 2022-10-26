package com.erdemklync.shopin.presentation.product_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemklync.shopin.data.remote.entity.Product
import com.erdemklync.shopin.domain.use_cases.product.ProductUseCases
import com.erdemklync.shopin.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow<DataState<Product>>(DataState.Loading())
    val state get() = _state.asStateFlow()

    init {
        savedStateHandle.get<Int>("productId")?.let { productId ->
            getProductById(productId)
        }
    }

    private fun getProductById(id: Int) = viewModelScope.launch {
        productUseCases.getProductById(id).let { dataState ->
            _state.value = dataState
        }
    }
}