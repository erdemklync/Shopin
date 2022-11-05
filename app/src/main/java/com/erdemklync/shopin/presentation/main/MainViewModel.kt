package com.erdemklync.shopin.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemklync.shopin.domain.use_cases.cart.CartUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cartUseCases: CartUseCases,
): ViewModel() {

    private val _cartTotal = MutableStateFlow(0.0)
    val cartTotal get() = _cartTotal.asStateFlow()

    init {
        getCartTotal()
    }

    private fun getCartTotal() = viewModelScope.launch {
        cartUseCases.getCart()?.collect { cartItems ->
            _cartTotal.value = cartItems.fold(0.0) { total, value ->
                total + (value.amount * value.product.price!!)
            }
        }
    }
}