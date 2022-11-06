package com.erdemklync.shopin.domain.use_cases.cart

import com.erdemklync.shopin.domain.repository.CartRepository
import javax.inject.Inject

class ClearCart @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke() {
        cartRepository.clearCart()
    }
}