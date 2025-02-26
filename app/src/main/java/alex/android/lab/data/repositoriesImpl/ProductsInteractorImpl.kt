package alex.android.lab.data.repositoriesImpl

import alex.android.lab.domain.entities.Product
import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.domain.repositories.ProductsRepository

class ProductsInteractorImpl(private val productsRepository: ProductsRepository) :
    ProductsInteractor {

    override suspend fun syncProductsWithApi() {
        return productsRepository.syncProductsWithApi()
    }

    override suspend fun checkInternetConnection(): Boolean {
        return productsRepository.checkInternetConnection()
    }

    override suspend fun getProducts(): List<Product> {
        return productsRepository.getProducts()
    }

    override suspend fun getProductById(guid: String): Product {
        return productsRepository.getProductById(guid)
    }

    override suspend fun updateProductViewCount(guid: String, viewCount: Int) {
        return productsRepository.updateProductViewCount(guid, viewCount)
    }

    override suspend fun updateProductInCartStatus(guid: String, isInCart: Boolean) {
        return productsRepository.updateProductInCartStatus(guid, isInCart)
    }

    override suspend fun updateProductFavoriteStatus(guid: String, isFavorite: Boolean) {
        return productsRepository.updateProductFavoriteStatus(guid, isFavorite)
    }
}