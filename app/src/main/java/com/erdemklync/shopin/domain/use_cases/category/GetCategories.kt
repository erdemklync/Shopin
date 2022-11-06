package com.erdemklync.shopin.domain.use_cases.category

import com.erdemklync.shopin.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategories @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): Result<List<String>> {
        return categoryRepository.getCategories()
    }
}