package com.erdemklync.shopin.data.remote.entity

data class CartItem(
    val product: Product = Product(),
    val amount: Int = 1,
)
