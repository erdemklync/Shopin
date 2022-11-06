package com.erdemklync.shopin.domain.use_cases.cart

import com.erdemklync.shopin.domain.model.Product
import com.erdemklync.shopin.domain.repository.CartRepository
import javax.inject.Inject

class RemoveFromCart @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(product: Product) {
        cartRepository.removeFromCart(product)
    }
}