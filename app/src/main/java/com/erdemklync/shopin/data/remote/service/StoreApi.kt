package com.erdemklync.shopin.data.remote.service

import com.erdemklync.shopin.data.remote.entity.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreApi {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): Product
}