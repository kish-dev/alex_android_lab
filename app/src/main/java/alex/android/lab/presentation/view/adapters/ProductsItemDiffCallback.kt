package alex.android.lab.presentation.view.adapters

import alex.android.lab.presentation.viewObject.ProductInListVO
import androidx.recyclerview.widget.DiffUtil

object ProductsItemDiffCallback : DiffUtil.ItemCallback<ProductInListVO>() {

    override fun areItemsTheSame(oldItem: ProductInListVO, newItem: ProductInListVO): Boolean {
        return oldItem.guid == newItem.guid
    }

    override fun areContentsTheSame(oldItem: ProductInListVO, newItem: ProductInListVO): Boolean {
        return oldItem == newItem
    }
}