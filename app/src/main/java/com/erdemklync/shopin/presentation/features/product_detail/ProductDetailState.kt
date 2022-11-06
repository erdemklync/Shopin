package com.erdemklync.shopin.presentation.features.product_detail

import com.erdemklync.shopin.domain.model.Product

data class ProductDetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val product: Product? = null,
    val amount: Int = 1,
)