package alex.android.lab.presentation.view.adapters

import alex.android.lab.databinding.ProductListItemBinding
import alex.android.lab.presentation.viewObject.ProductInListVO
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide

class ProductsAdapter :
    ListAdapter<ProductInListVO, ProductsViewHolder>(ProductsItemDiffCallback) {

    private var onProductClickListener: OnProductClickListener? = null

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
        val params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams

        if (position == currentList.size - 1) {
            params.bottomMargin = 50  // 50px (можно перевести в dp)
        } else {
            params.bottomMargin = 0
        }
        holder.itemView.layoutParams = params

        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(product.image)
                .into(productIV)
            nameTV.text = product.name
            priceTV.text = product.price
            ratingView.rating = product.rating.toFloat()
            root.setOnClickListener {
                onProductClickListener?.onProductClick(product)
            }
        }
    }

    fun setupOnProductClickListener(onProductClickListener: OnProductClickListener) {
        this.onProductClickListener = onProductClickListener
    }

    interface OnProductClickListener {
        fun onProductClick(product: ProductInListVO)
    }
}