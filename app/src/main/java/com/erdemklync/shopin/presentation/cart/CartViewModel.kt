package com.erdemklync.shopin.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemklync.shopin.data.remote.entity.CartItem
import com.erdemklync.shopin.data.remote.entity.Product
import com.erdemklync.shopin.domain.use_cases.cart.CartUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCases: CartUseCases,
): ViewModel() {

    private val _state = MutableStateFlow(CartDataState())
    val state get() = _state.asStateFlow()

    init {
        getCart()
    }

    private fun getCart() = viewModelScope.launch {
        cartUseCases.getCart()?.collect { cartItems ->
            val total = cartItems.fold(0.0) { total, value ->
                total + (value.amount * value.product.price!!)
            }
            _state.update {
                it.copy(
                    cartItems = cartItems,
                    total = total
                )
            }
        }
    }

    fun increaseAmount(cartItem: CartItem) = viewModelScope.launch {
        cartUseCases.increaseAmount(cartItem.product)
    }
    fun decreaseAmount(
        cartItem: CartItem,
        showRemoveFromCartDialog:(Product) -> Unit,
    ) = viewModelScope.launch {
        if (cartItem.amount > 1) {
            cartUseCases.decreaseAmount(cartItem.product)
        } else {
            showRemoveFromCartDialog(cartItem.product)
        }
    }

    fun removeProductFromCart(product: Product) = viewModelScope.launch {
        cartUseCases.removeFromCart(product)
    }

    fun clearCart() {
        cartUseCases.clearCart()
    }
}