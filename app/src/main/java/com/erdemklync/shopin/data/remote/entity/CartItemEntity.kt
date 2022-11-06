package com.erdemklync.shopin.data.remote.entity

import com.google.gson.annotations.SerializedName

data class CartItemEntity(
    @SerializedName("product")
    val product: ProductEntity? = ProductEntity(),
    @SerializedName("amount")
    val amount: Int? = 1,
)

