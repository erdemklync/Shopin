package com.erdemklync.shopin.domain.use_cases.product

import com.erdemklync.shopin.domain.model.Product
import com.erdemklync.shopin.domain.repository.ProductRepository
import javax.inject.Inject

class GetProducts @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): Result<List<Product>> {
        return productRepository.getProducts()
    }
}