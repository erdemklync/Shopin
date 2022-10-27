package com.erdemklync.shopin.domain.use_cases.product

import com.erdemklync.shopin.data.remote.entity.Product
import com.erdemklync.shopin.domain.repository.ProductRepository
import com.erdemklync.shopin.util.DataState
import javax.inject.Inject

class GetProductById @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(id: Int) : DataState<Product> {
        return try {
            DataState.Success(
                data = productRepository.getProductById(id)
            )
        }catch (e: Exception){
            e.printStackTrace()
            DataState.Error(e.toString())
        }
    }
}