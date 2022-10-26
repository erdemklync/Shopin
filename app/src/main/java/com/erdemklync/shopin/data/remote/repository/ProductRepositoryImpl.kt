package com.erdemklync.shopin.data.remote.repository

import com.erdemklync.shopin.data.remote.entity.Product
import com.erdemklync.shopin.data.remote.service.StoreApi
import com.erdemklync.shopin.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val storeApi: StoreApi
) : ProductRepository{
    override suspend fun getProducts(): List<Product> {
        return storeApi.getProducts()
    }

    override suspend fun getProductById(id: Int): Product {
        return storeApi.getProductById(id)
    }
}