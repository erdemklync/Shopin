package com.erdemklync.shopin.util

import android.text.SpannableStringBuilder
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.bold
import com.bumptech.glide.Glide
import com.erdemklync.shopin.R
import com.erdemklync.shopin.data.remote.entity.Rating

infix fun ImageView.setProductImage(imageUrl: String?) {
    imageUrl?.let {
        Glide.with(this).load(it).into(this)
    }
}

infix fun TextView.setPrice(price: Double?) {
    this.text = String.format("$%.2f".format(price))
}

infix fun TextView.setProductRating(rating: Rating?) {
    rating?.let {
        this.text = SpannableStringBuilder()
            .bold {
                append(it.rate.toString())
            }.append(" ${resources.getQuantityString(R.plurals.review, rating.count!!, rating.count)}")
    }
}