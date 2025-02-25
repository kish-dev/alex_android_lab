package alex.android.lab.domain.entities

import alex.android.lab.presentation.viewObject.ProductInListVO

sealed class ProductDetailState {

    object Idle : ProductDetailState()

    object Loading : ProductDetailState()

    data class Loaded(val product: ProductInListVO) : ProductDetailState()

    data class Error(val error: String) : ProductDetailState()
}