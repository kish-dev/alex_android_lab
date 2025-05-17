package com.example.core_utils.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.example.core_model.presentation.ProductInListVO

object ProductsItemDiffCallback : DiffUtil.ItemCallback<ProductInListVO>() {

    val KEY_IN_CART_COUNT = "inCartCount"

    override fun areItemsTheSame(oldItem: ProductInListVO, newItem: ProductInListVO): Boolean {
        return oldItem.guid == newItem.guid
    }

    override fun areContentsTheSame(oldItem: ProductInListVO, newItem: ProductInListVO): Boolean {
        return oldItem.guid == newItem.guid &&
                oldItem.image == newItem.image &&
                oldItem.name == newItem.name &&
                oldItem.price == newItem.price &&
                oldItem.rating == newItem.rating &&
                oldItem.inCartCount == newItem.inCartCount
    }

    override fun getChangePayload(oldItem: ProductInListVO, newItem: ProductInListVO): Any? {
        val diffBundle = Bundle()

        if (oldItem.inCartCount != newItem.inCartCount) {
            diffBundle.putInt(KEY_IN_CART_COUNT, newItem.inCartCount)
        }

        return if (diffBundle.isEmpty) null else diffBundle
    }
}