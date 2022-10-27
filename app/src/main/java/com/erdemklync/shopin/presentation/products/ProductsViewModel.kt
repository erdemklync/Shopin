package com.erdemklync.shopin.presentation.products

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
class ProductsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
): ViewModel() {

    private val _state = MutableStateFlow<DataState<List<Product>>>(DataState.Loading())
    val state get() = _state.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() = viewModelScope.launch {
        productUseCases.getProducts().let { dataState ->
            _state.value = dataState
        }
    }
}