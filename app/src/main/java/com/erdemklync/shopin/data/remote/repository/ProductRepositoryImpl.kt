package com.erdemklync.shopin.data.remote.repository

import com.erdemklync.shopin.data.remote.entity.Product
import com.erdemklync.shopin.data.remote.service.StoreApi
import com.erdemklync.shopin.domain.repository.ProductRepository
import com.erdemklync.shopin.util.DataState
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val storeApi: StoreApi
) : ProductRepository{
    override suspend fun getProducts(): DataState<List<Product>> {
        return try {
            DataState.Success(
                data = storeApi.getProducts()
            )
        }catch (e: Exception){
            e.printStackTrace()
            DataState.Error(e.toString())
        }
    }
}