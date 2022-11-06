package com.erdemklync.shopin.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.erdemklync.shopin.data.remote.entity.Product
import com.erdemklync.shopin.databinding.ItemSearchBinding
import com.erdemklync.shopin.util.setPrice
import com.erdemklync.shopin.util.setProductImage


class SearchAdapter(
    private val onClick:(Product) -> Unit,
) : ListAdapter<Product, SearchAdapter.ProductsViewHolder>(ProductsDiffUtil()) {

    class ProductsViewHolder(
        private val binding: ItemSearchBinding
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
        val binding = ItemSearchBinding.inflate(
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