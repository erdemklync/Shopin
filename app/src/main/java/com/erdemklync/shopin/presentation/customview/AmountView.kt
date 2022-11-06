package com.erdemklync.shopin.presentation.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.erdemklync.shopin.R
import com.erdemklync.shopin.databinding.ViewAmountBinding
import kotlin.properties.Delegates

class AmountView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): ConstraintLayout(context, attrs, defStyle) {

    private val binding = ViewAmountBinding.inflate(LayoutInflater.from(context), this)

    val buttonIncrease = binding.buttonIncrease
    val buttonDecrease = binding.buttonDecrease

    private var maxLimit: Int
    private var minLimit: Int

    var amount: Int by Delegates.observable(1) { _, _, newValue ->
        binding.textAmount.text = newValue.toString()
        if (newValue == 1) {
            binding.buttonDecrease.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_delete, context.theme)
        } else {
            binding.buttonDecrease.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_remove, context.theme)
        }
    }

    init {
        background = ResourcesCompat.getDrawable(resources, R.drawable.bg_amount_view, context.theme)

        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.AmountView,
            defStyle,
            0
        )

        amount = typedArray.getInt(R.styleable.AmountView_amount, 1)
        maxLimit = typedArray.getInt(R.styleable.AmountView_maxLimit, 99)
        minLimit = typedArray.getInt(R.styleable.AmountView_maxLimit, 1)

        binding.textAmount.text = amount.toString()

        binding.buttonIncrease.setOnClickListener {
            increase()
        }

        binding.buttonDecrease.setOnClickListener {
            decrease()
        }

        typedArray.recycle()
    }

    private fun increase() {
        if (amount < maxLimit) {
            amount++
        }
    }

    private fun decrease() {
        if (amount > minLimit) {
            amount--
        }
    }

}