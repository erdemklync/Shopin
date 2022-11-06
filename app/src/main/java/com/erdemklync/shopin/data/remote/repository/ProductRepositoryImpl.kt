package com.erdemklync.shopin.data.remote.repository

import com.erdemklync.shopin.data.mapper.ProductMapper
import com.erdemklync.shopin.data.remote.service.StoreApi
import com.erdemklync.shopin.domain.model.Product
import com.erdemklync.shopin.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val storeApi: StoreApi,
    private val mapper: ProductMapper,
) : ProductRepository{
    override suspend fun getProducts(): Result<List<Product>> {
        return try {
            Result.success(
                mapper.mapFromEntityList(storeApi.getProducts())
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        return try {
            Result.success(
                mapper.mapFromEntity(storeApi.getProductById(id))
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}