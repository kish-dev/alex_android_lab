package alex.android.lab.domain.entities

import alex.android.lab.presentation.viewObject.ProductInListVO

sealed class ProductsListState {

    object Idle : ProductsListState()

    object Loading : ProductsListState()

    data class Loaded(val products: List<ProductInListVO>) :
        ProductsListState()

    data class Error(val error: String) : ProductsListState()
}