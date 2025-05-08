package com.example.core_utils.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.core_utils.databinding.ProductListItemBinding

class ProductsViewHolder(val binding: ProductListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun updateInCartCount(inCartCount: Int) {
        binding.cartButton.setState(inCartCount)
    }
}