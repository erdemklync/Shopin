package com.erdemklync.shopin.domain.use_cases.product

import com.erdemklync.shopin.data.remote.entity.Product
import com.erdemklync.shopin.domain.repository.ProductRepository
import com.erdemklync.shopin.util.DataState
import javax.inject.Inject

class GetProducts @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): DataState<List<Product>> {
        return productRepository.getProducts()
    }
}