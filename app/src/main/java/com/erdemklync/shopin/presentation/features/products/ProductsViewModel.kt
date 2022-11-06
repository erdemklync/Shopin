package com.erdemklync.shopin.presentation.features.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemklync.shopin.domain.use_cases.product.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
): ViewModel() {

    private val _state = MutableStateFlow(ProductsDataState())
    val state get() = _state.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() = viewModelScope.launch(Dispatchers.IO) {
        productUseCases.getProducts().let { result ->
            result.fold(
                onSuccess = { products ->
                    _state.emit(
                        ProductsDataState(
                            isLoading = false,
                            products = products
                        )
                    )
                },
                onFailure = { error ->
                    _state.emit(
                        ProductsDataState(
                            isLoading = false,
                            error = error.message
                        )
                    )
                }
            )
        }
    }
}