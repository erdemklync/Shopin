package com.erdemklync.shopin.presentation.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erdemklync.shopin.data.remote.entity.Product
import com.erdemklync.shopin.databinding.ItemProductBinding

class ProductsAdapter(
    private val onClick:(Product) -> Unit,
) : ListAdapter<Product, ProductsAdapter.ProductsViewHolder>(ProductsDiffUtil()) {

    class ProductsViewHolder(
        private val binding: ItemProductBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(product: Product, onClick:(Product) -> Unit) {
            with(binding) {
                Glide
                    .with(binding.root)
                    .load(product.image)
                    .into(binding.imageProduct)

                textProductTitle.text = product.title
                textProductPrice.text = product.price.toString()
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