package com.erdemklync.shopin.domain.repository

import com.erdemklync.shopin.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
    suspend fun getProductById(id: Int): Result<Product>
}