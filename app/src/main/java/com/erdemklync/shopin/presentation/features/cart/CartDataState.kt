package com.erdemklync.shopin.presentation.features.cart

import com.erdemklync.shopin.domain.model.CartItem

data class CartDataState(
    val cartItems: List<CartItem> = emptyList(),
    val total: Double = 0.0
)
