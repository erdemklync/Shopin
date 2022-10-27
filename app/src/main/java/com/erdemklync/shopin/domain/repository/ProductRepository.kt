package com.erdemklync.shopin.domain.repository

import com.erdemklync.shopin.data.remote.entity.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProductById(id: Int): Product
}