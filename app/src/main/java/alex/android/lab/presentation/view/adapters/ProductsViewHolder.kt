package alex.android.lab.presentation.view.adapters

import alex.android.lab.databinding.ProductListItemBinding
import androidx.recyclerview.widget.RecyclerView

class ProductsViewHolder(val binding: ProductListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun updateInCartCount(inCartCount: Int) {
        binding.cartButton.setState(inCartCount)
    }
}