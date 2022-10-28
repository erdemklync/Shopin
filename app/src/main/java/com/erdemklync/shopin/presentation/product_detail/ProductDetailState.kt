package com.erdemklync.shopin.presentation.product_detail

import com.erdemklync.shopin.data.remote.entity.Product
import com.erdemklync.shopin.util.DataState

data class ProductDetailState(
    val dataState: DataState<Product> = DataState.Loading(),
    val amount: Int = 1,
)
