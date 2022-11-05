package com.erdemklync.shopin.domain.use_cases.cart

import com.erdemklync.shopin.data.remote.entity.Product
import com.erdemklync.shopin.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject

class AddToCart @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) {
    operator fun invoke(
        product: Product,
        amount: Int,
        onSuccess:() -> Unit,
    ) {

        val productData = hashMapOf(
            Constants.FIELD_PRODUCT to hashMapOf(
                Constants.FIELD_PRODUCT_ID to product.id,
                Constants.FIELD_PRODUCT_IMAGE to product.image,
                Constants.FIELD_PRODUCT_TITLE to product.title,
                Constants.FIELD_PRODUCT_DESCRIPTION to product.description,
                Constants.FIELD_PRODUCT_RATING to mapOf(
                    "count" to product.rating?.count,
                    "rate" to product.rating?.rate,
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
}