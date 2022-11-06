package com.erdemklync.shopin.presentation.features.search

import com.erdemklync.shopin.domain.model.Product

data class SearchDataState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val products: List<Product> = emptyList(),
    val categories: List<String> = emptyList(),
    val query: String = "",
    val selectedCategories: MutableList<String> = mutableListOf(),
)