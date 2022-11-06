package com.erdemklync.shopin.presentation.features.products

import com.erdemklync.shopin.domain.model.Product

data class ProductsDataState(
    val isLoading: Boolean = true,
    val products: List<Product> = emptyList(),
    val error: String? = null
)