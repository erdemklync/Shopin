package com.erdemklync.shopin.domain.use_cases.product

import com.erdemklync.shopin.domain.repository.ProductRepository
import com.erdemklync.shopin.util.DataState
import javax.inject.Inject

class GetCategories @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): DataState<List<String>> {
        return try {
            DataState.Success(
                data = productRepository.getCategories()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            DataState.Error(e.toString())
        }
    }
}