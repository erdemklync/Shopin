package com.erdemklync.shopin.presentation.features.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.erdemklync.shopin.databinding.ItemProductBinding
import com.erdemklync.shopin.domain.model.Product
import com.erdemklync.shopin.presentation.util.setPrice
import com.erdemklync.shopin.presentation.util.setProductImage

class ProductAdapter(
    private val onClick:(Product) -> Unit,
) : ListAdapter<Product, ProductAdapter.ProductsViewHolder>(ProductsDiffUtil()) {

    class ProductsViewHolder(
        private val binding: ItemProductBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(product: Product, onClick:(Product) -> Unit) {
            with(binding) {
                imageProduct setProductImage product.image
                textProductTitle.text = product.title
                textProductPrice setPrice product.price
                root.setOnClickListener {
                    onClick(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product, onClick)
    }

    class ProductsDiffUtil : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }
}