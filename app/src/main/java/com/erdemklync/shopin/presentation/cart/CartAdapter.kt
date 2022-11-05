package com.erdemklync.shopin.presentation.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.erdemklync.shopin.data.remote.entity.CartItem
import com.erdemklync.shopin.databinding.ItemCartBinding
import com.erdemklync.shopin.util.setPrice
import com.erdemklync.shopin.util.setProductImage
class CartAdapter(
    val onIncrease:(CartItem) -> Unit,
    val onDecrease:(CartItem) -> Unit,
): ListAdapter<CartItem, CartAdapter.CartViewHolder>(ProductsDiffUtil()) {

    inner class CartViewHolder(
        private val binding: ItemCartBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(cartItem: CartItem) {
            with(binding) {
                imageProduct setProductImage cartItem.product.image
                textProductTitle.text = cartItem.product.title
                textProductPrice setPrice cartItem.product.price?.times(cartItem.amount)

                amountView.amount = cartItem.amount
                amountView.buttonIncrease.setOnClickListener {
                    onIncrease(cartItem)
                }

                amountView.buttonDecrease.setOnClickListener {
                    onDecrease(cartItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    class ProductsDiffUtil : DiffUtil.ItemCallback<CartItem>(){
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem) = oldItem.product.id == oldItem.product.id
        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem) = oldItem == newItem
    }
}