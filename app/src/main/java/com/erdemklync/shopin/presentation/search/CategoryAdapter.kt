package com.erdemklync.shopin.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.erdemklync.shopin.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val onCheck: (String) -> Unit,
) : ListAdapter<String, CategoryAdapter.ChipViewHolder>(ProductsDiffUtil()) {

    class ChipViewHolder(
        private val binding: ItemCategoryBinding
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(category: String, onCheck: (String) -> Unit) {
            with(binding) {
                root.text = category
                root.setOnCheckedChangeListener { _, _ ->
                    onCheck(category)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChipViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChipViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product, onCheck)
    }

    class ProductsDiffUtil : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }
}