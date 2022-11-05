package com.erdemklync.shopin.data.remote.entity

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("count")
    val count: Int? = 0,
    @SerializedName("rate")
    val rate: Double? = 0.0
)