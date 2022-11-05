package com.erdemklync.shopin.presentation.product_detail

import com.erdemklync.shopin.data.remote.entity.Product

data class ProductDetailState(
    //val dataState: DataState<Product> = DataState.Loading(),
    val product: Product? = null,
    val amount: Int = 1,
)