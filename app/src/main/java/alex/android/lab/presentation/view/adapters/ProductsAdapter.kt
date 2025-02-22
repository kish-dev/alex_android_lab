package alex.android.lab.presentation.view.adapters

import alex.android.lab.databinding.ProductListItemBinding
import alex.android.lab.presentation.viewObject.ProductInListVO
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide

class ProductsAdapter :
    ListAdapter<ProductInListVO, ProductsViewHolder>(ProductsItemDiffCallback) {

    var onProductClickListener: OnProductClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ProductListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = getItem(position)
        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(product.image)
                .into(productIV)
            nameTV.text = product.name
            priceTV.text = product.price
            ratingView.rating = product.rating.toFloat()
            root.setOnClickListener {
                onProductClickListener?.onProductClick(product.guid)
                Log.d("LOG_TAG", "clicked to product and ${product.guid}")
            }
        }
    }

    interface OnProductClickListener {
        fun onProductClick(guid: String)
    }
}