package com.erdemklync.shopin.presentation.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.erdemklync.shopin.R
import com.erdemklync.shopin.databinding.ViewAddToCartBinding
import kotlin.properties.Delegates

class AddToCartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): ConstraintLayout(context, attrs, defStyle) {

    private val binding = ViewAddToCartBinding.inflate(LayoutInflater.from(context), this)

    val buttonAddToCart = binding.buttonAddToCart
    val buttonIncrease = binding.buttonIncrease
    val buttonDecrease = binding.buttonDecrease

    private var maxLimit: Int
    private var minLimit: Int

    var amount: Int by Delegates.observable(1) { _, _, newValue ->
        binding.textAmount.text = newValue.toString()
    }

    init {
        background = ResourcesCompat.getDrawable(resources, R.drawable.bg_add_to_cart_view, context.theme)

        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.AddToCartView,
            defStyle,
            0
        )

        amount = typedArray.getInt(R.styleable.AddToCartView_addToCartAmount, 1)
        maxLimit = typedArray.getInt(R.styleable.AddToCartView_addToCartMaxLimit, 99)
        minLimit = typedArray.getInt(R.styleable.AddToCartView_addToCartMinLimit, 1)

        binding.textAmount.text = amount.toString()

        buttonIncrease.setOnClickListener {
            increase()
        }

        buttonDecrease.setOnClickListener {
            decrease()
        }

        typedArray.recycle()
    }

    private fun increase() {
        if (amount < maxLimit) {
            amount++
            binding.textAmount.text = amount.toString()
        }
    }

    private fun decrease() {
        if (amount > minLimit) {
            amount--
            binding.textAmount.text = amount.toString()
        }
    }
}