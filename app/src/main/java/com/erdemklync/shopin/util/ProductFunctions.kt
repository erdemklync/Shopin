package com.erdemklync.shopin.util

import android.text.SpannableStringBuilder
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.bold
import com.bumptech.glide.Glide
import com.erdemklync.shopin.data.remote.entity.Rating
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

infix fun TextView.setProductRating(rating: Rating) {
    this.text = SpannableStringBuilder()
        .bold {
            append(rating.rate.toString())
        }.append(" (${rating.count} reviews)")
}