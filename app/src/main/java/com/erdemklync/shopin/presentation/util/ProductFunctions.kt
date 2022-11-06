package com.erdemklync.shopin.presentation.util

import android.text.SpannableStringBuilder
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.bold
import com.bumptech.glide.Glide
import com.erdemklync.shopin.R
import com.erdemklync.shopin.domain.model.Rating

infix fun ImageView.setProductImage(url: String) {
    Glide.with(this).load(url).into(this)
}

infix fun TextView.setPrice(price: Double) {
    this.text = String.format("$%.2f".format(price))
}

infix fun TextView.setProductRating(rating: Rating) {
    this.text = SpannableStringBuilder()
        .bold {
            append(rating.rate.toString())
        }.append(" ${resources.getQuantityString(R.plurals.review, rating.count, rating.count)}")
}