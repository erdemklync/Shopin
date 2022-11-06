package com.erdemklync.shopin.domain.use_cases.cart

import com.erdemklync.shopin.domain.model.CartItem
import com.erdemklync.shopin.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCart @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(): Flow<List<CartItem>>? {
        return cartRepository.getCart()
    }
}