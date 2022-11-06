package com.erdemklync.shopin.data.remote.repository

import com.erdemklync.shopin.data.mapper.CartMapper
import com.erdemklync.shopin.data.remote.entity.CartItemEntity
import com.erdemklync.shopin.domain.model.CartItem
import com.erdemklync.shopin.domain.model.Product
import com.erdemklync.shopin.domain.repository.CartRepository
import com.erdemklync.shopin.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartMapper: CartMapper,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : CartRepository{

    override fun getCart(): Flow<List<CartItem>>? {
        return firebaseAuth.currentUser?.let { user ->
             firestore
                .collection(Constants.USERS)
                .document(user.uid)
                .collection(Constants.CART)
                .snapshots()
                .map {
                    cartMapper.mapFromEntityList(
                        it.toObjects(CartItemEntity::class.java)
                    )
                }
        }
    }

    override fun addToCart(product: Product, amount: Int, onSuccess: () -> Unit) {

        val productData = hashMapOf(
            Constants.FIELD_PRODUCT to hashMapOf(
                Constants.FIELD_PRODUCT_ID to product.id,
                Constants.FIELD_PRODUCT_IMAGE to product.image,
                Constants.FIELD_PRODUCT_TITLE to product.title,
                Constants.FIELD_PRODUCT_CATEGORY to product.category,
                Constants.FIELD_PRODUCT_DESCRIPTION to product.description,
                Constants.FIELD_PRODUCT_RATING to mapOf(
                    Constants.FIELD_PRODUCT_RATING_COUNT to product.rating.count,
                    Constants.FIELD_PRODUCT_RATING_RATE to product.rating.rate,
                ),
                Constants.FIELD_PRODUCT_PRICE to product.price,
            ),
            Constants.FIELD_PRODUCT_AMOUNT to FieldValue.increment(amount.toLong())
        )

        firebaseAuth.currentUser?.let { user ->
            firestore
                .collection(Constants.USERS)
                .document(user.uid)
                .collection(Constants.CART)
                .document(product.id.toString())
                .set(productData, SetOptions.merge())
                .addOnSuccessListener {
                    onSuccess()
                }
        }
    }

    override fun changeAmount(product: Product, amount: Int) {
        firebaseAuth.currentUser?.let { user ->
            firestore
                .collection(Constants.USERS)
                .document(user.uid)
                .collection(Constants.CART)
                .document(product.id.toString())
                .update(Constants.FIELD_PRODUCT_AMOUNT, FieldValue.increment(amount.toLong()))
        }
    }

    override fun clearCart() {
        firebaseAuth.currentUser?.let { user ->
            firestore
                .collection(Constants.USERS)
                .document(user.uid)
                .collection(Constants.CART)
                .get()
                .addOnSuccessListener { snapshot ->
                    snapshot.forEach { docs ->
                        docs.reference.delete()
                    }
                }
        }
    }

    override fun removeFromCart(product: Product) {
        firebaseAuth.currentUser?.let { user ->
            firestore
                .collection(Constants.USERS)
                .document(user.uid)
                .collection(Constants.CART)
                .document(product.id.toString())
                .delete()
        }
    }
}