package com.erdemklync.shopin.util

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

infix fun ImageView.setProductImage(imageUrl: String) {
    Glide.with(this).load(imageUrl).into(this)
}

infix fun TextView.setProductPrice(price: Double) {
    val symbols = DecimalFormatSymbols(Locale.US)
    DecimalFormat("0.00", symbols).format(price).plus(" $").also {
        this.text = it
    }
}

infix fun RatingBar.setProductRatingRate(rate: Double) {
    this.rating = rate.toFloat()
}

infix fun TextView.setProductRatingCount(count: Int) {
    this.text = count.toString().plus(" comments")
}