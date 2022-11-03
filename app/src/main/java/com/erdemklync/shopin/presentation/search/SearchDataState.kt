package com.erdemklync.shopin.presentation.search

import com.erdemklync.shopin.data.remote.entity.Product

data class SearchDataState(
    val products: List<Product> = emptyList(),
    val categories: List<String> = emptyList(),
    val query: String = "",
    val selectedCategories: MutableList<String> = mutableListOf(),
)