package com.erdemklync.shopin.data.remote.service

import com.erdemklync.shopin.data.remote.entity.Product
import retrofit2.Call
import retrofit2.http.GET

interface StoreApi {
    @GET("products")
    fun getProducts(): Call<List<Product>>
}