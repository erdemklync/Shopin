package com.erdemklync.shopin.presentation.cart

import com.erdemklync.shopin.data.remote.entity.CartItem

data class CartDataState(
    val cartItems: List<CartItem> = emptyList(),
    val total: Double = 0.0
)
