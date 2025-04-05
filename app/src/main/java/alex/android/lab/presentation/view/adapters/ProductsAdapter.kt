package alex.android.lab.presentation.view.adapters

import alex.android.lab.databinding.ProductListItemBinding
import alex.android.lab.presentation.viewObject.ProductInListVO
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide

class ProductsAdapter :
    ListAdapter<ProductInListVO, ProductsViewHolder>(ProductsItemDiffCallback) {

    private var onProductClickListener: OnProductClickListener? = null
    private var updateProductInCartCount: UpdateProductInCartCount? = null

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
            cartButton.setState(product.inCartCount)
            cartButton.updateProductInCartCount = { inCartCount ->
                updateProductInCartCount?.updateProductInCart(product.guid, inCartCount)
            }
        }
    }

    override fun onBindViewHolder(
        holder: ProductsViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val bundle = payloads[0] as? Bundle
            bundle?.let {
                if (it.containsKey(ProductsItemDiffCallback.KEY_IN_CART_COUNT)) {
                    val inCartCount = it.getInt(ProductsItemDiffCallback.KEY_IN_CART_COUNT)
                    holder.updateInCartCount(inCartCount)
                }
            }
        }
    }

    fun setupOnProductClickListener(onProductClickListener: OnProductClickListener) {
        this.onProductClickListener = onProductClickListener
    }

    fun setupUpdateProductInCartCount(updateProductInCartCount: UpdateProductInCartCount) {
        this.updateProductInCartCount = updateProductInCartCount
    }

    interface OnProductClickListener {
        fun onProductClick(product: ProductInListVO)
    }

    interface UpdateProductInCartCount {
        fun updateProductInCart(guid: String, inCartCount: Int)
    }
}