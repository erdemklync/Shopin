package com.erdemklync.shopin.data.remote.repository

import com.erdemklync.shopin.data.remote.service.StoreApi
import com.erdemklync.shopin.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val storeApi: StoreApi
): CategoryRepository {
    override suspend fun getCategories(): Result<List<String>> {
        return try {
            Result.success(
                storeApi.getCategories()
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}