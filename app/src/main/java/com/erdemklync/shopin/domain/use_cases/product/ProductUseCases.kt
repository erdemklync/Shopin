package com.erdemklync.shopin.domain.use_cases.product

import javax.inject.Inject

data class ProductUseCases @Inject constructor(
    val getProducts: GetProducts,
    val getProductById: GetProductById,
)
