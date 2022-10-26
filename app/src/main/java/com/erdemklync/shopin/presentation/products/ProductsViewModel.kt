package com.erdemklync.shopin.presentation.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erdemklync.shopin.data.remote.entity.Product
import com.erdemklync.shopin.domain.repository.ProductRepository
import com.erdemklync.shopin.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductsDataState(
    val products: List<Product> = emptyList()
)

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    private val _state = MutableStateFlow(ProductsDataState())
    val state get() = _state.asStateFlow()

    init {
        Log.d("RESULT", "ViewModel created.")
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProducts().let {
                when(val result = it){
                    is DataState.Loading -> TODO()
                    is DataState.Error -> {
                        Log.d("RESULT", result.message)
                    }
                    is DataState.Success -> {
                        Log.d("RESULT", result.data.toString())
                    }
                }
            }
        }
    }
}