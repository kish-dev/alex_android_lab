package alex.android.lab.domain.repositories

import alex.android.lab.domain.entities.Product

interface ProductsRepository {

    suspend fun syncProductsWithApi()

    suspend fun checkInternetConnection(): Boolean

    suspend fun getProducts(): List<Product>

    suspend fun getProductsInCart(): List<Product>

    suspend fun getProductById(guid: String): Product

    suspend fun updateProductViewCount(guid: String, viewCount: Int)

    suspend fun updateProductInCartCount(guid: String, inCartCount: Int)

    suspend fun updateProductFavoriteStatus(guid: String, isFavorite: Boolean)
}