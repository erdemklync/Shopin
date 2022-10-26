package com.erdemklync.shopin.data.remote.service

import com.erdemklync.shopin.data.remote.entity.Product
import retrofit2.http.GET

interface StoreApi {
    @GET("products")
    suspend fun getProducts(): List<Product>
}