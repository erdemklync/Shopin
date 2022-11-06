package com.erdemklync.shopin.presentation.features.product_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemklync.shopin.domain.use_cases.cart.CartUseCases
import com.erdemklync.shopin.domain.use_cases.product.ProductUseCases
import com.erdemklync.shopin.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val cartUseCases: CartUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(ProductDetailState())
    val state get() = _state.asStateFlow()

    init {
        savedStateHandle.get<Int>(Constants.ARGUMENT_PRODUCT_ID)?.let { productId ->
            getProductById(productId)
        }
    }

    private fun getProductById(id: Int) = viewModelScope.launch {
        productUseCases.getProductById(id).let { result ->
            result.fold(
                onSuccess = { product ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            product = product
                        )
                    }
                },
                onFailure = { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                }
            )
        }
    }

    fun incrementAmount() {
        if(state.value.amount < 99) {
            _state.update {
                it.copy(
                    amount = it.amount + 1
                )
            }
        }
    }

    fun decrementAmount() {
        if (state.value.amount > 1){
            _state.update {
                it.copy(
                    amount = it.amount - 1
                )
            }
        }
    }

    fun addToCart(
        onSuccess:() -> Unit,
    ) = viewModelScope.launch {
        state.value.product?.let {
            cartUseCases.addToCart(
                product = it,
                amount = state.value.amount,
                onSuccess = onSuccess
            )
        }
    }
}