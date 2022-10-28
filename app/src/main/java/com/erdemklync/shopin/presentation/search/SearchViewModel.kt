package com.erdemklync.shopin.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemklync.shopin.domain.use_cases.product.ProductUseCases
import com.erdemklync.shopin.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
): ViewModel() {

    private val _state = MutableStateFlow(SearchDataState())
    val state get() = _state.asStateFlow()
    val filteredList
        get() = _state.map {
            if(it.query.length > 2) {
                it.products.filter { product ->
                    product.title.contains(it.query, true)
                }
            } else {
                it.products
            }
        }

    init {
        getProducts()
        getCategories()
    }

    private fun getProducts() = viewModelScope.launch {
        productUseCases.getProducts().let { dataState ->
            when(dataState){
                is DataState.Success -> {
                    _state.update {
                        it.copy(
                            products = dataState.data
                        )
                    }
                }
                is DataState.Error -> TODO()
                is DataState.Loading -> TODO()
            }
        }
    }

    private fun getCategories() = viewModelScope.launch {
        productUseCases.getCategories().let { dataState ->
            if(dataState is DataState.Success) {
                _state.update {
                    it.copy(
                        categories = dataState.data
                    )
                }
            }
        }
    }

    fun setQuery(query: String) {
        _state.update {
            it.copy(
                query = query
            )
        }
    }

    fun selectFilter(filter: String) {
        val selectedCategories = state.value.selectedCategories.toMutableSet()

        if(selectedCategories.contains(filter)) {
            selectedCategories.remove(filter)
        } else {
            selectedCategories.add(filter)
        }

        _state.update {
            it.copy(
                selectedCategories = selectedCategories
            )
        }
    }
}