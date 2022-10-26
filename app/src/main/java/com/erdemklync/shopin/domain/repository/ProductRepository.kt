package com.erdemklync.shopin.domain.repository

import com.erdemklync.shopin.data.remote.entity.Product
import com.erdemklync.shopin.util.DataState

interface ProductRepository {
    suspend fun getProducts(): DataState<List<Product>>
}