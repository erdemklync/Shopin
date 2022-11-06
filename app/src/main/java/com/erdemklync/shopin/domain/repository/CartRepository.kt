package com.erdemklync.shopin.domain.repository

import com.erdemklync.shopin.domain.model.CartItem
import com.erdemklync.shopin.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCart(): Flow<List<CartItem>>?
    fun addToCart(product: Product, amount: Int, onSuccess:() -> Unit)
    fun changeAmount(product: Product, amount: Int)
    fun clearCart()
    fun removeFromCart(product: Product)
}