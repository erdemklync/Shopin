package com.erdemklync.shopin.domain.repository

interface CategoryRepository {
    suspend fun getCategories(): Result<List<String>>
}