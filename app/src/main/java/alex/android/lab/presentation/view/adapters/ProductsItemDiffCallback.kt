package alex.android.lab.presentation.view.adapters

import alex.android.lab.presentation.viewObject.ProductInListVO
import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil

object ProductsItemDiffCallback : DiffUtil.ItemCallback<ProductInListVO>() {

    val KEY_IN_CART_COUNT = "inCartCount"

    override fun areItemsTheSame(oldItem: ProductInListVO, newItem: ProductInListVO): Boolean {
        return oldItem.guid == newItem.guid
    }

    override fun areContentsTheSame(oldItem: ProductInListVO, newItem: ProductInListVO): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: ProductInListVO, newItem: ProductInListVO): Any? {
        val diffBundle = Bundle()

        if (oldItem.inCartCount != newItem.inCartCount) {
            diffBundle.putInt(KEY_IN_CART_COUNT, newItem.inCartCount)
        }

        return if (diffBundle.isEmpty) null else diffBundle
    }
}