package alex.android.lab.domain.interactors

import alex.android.lab.domain.entities.Product

interface ProductsInteractor {

    suspend fun syncProductsWithApi()

    suspend fun checkInternetConnection(): Boolean

    suspend fun getProducts(): List<Product>

    suspend fun getProductsInCart(): List<Product>

    suspend fun getProductById(guid: String): Product

    suspend fun updateProductViewCount(guid: String, viewCount: Int)

    suspend fun updateProductInCartStatus(guid: String, isInCart: Boolean)

    suspend fun updateProductInCartCount(guid: String, inCartCount: Int)

    suspend fun updateProductFavoriteStatus(guid: String, isFavorite: Boolean)
}