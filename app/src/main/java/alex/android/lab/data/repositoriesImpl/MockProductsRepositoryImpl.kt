package alex.android.lab.data.repositoriesImpl

import alex.android.lab.data.local.db.ProductsDao
import alex.android.lab.data.mapper.ProductMapper
import alex.android.lab.data.network.ApiService
import alex.android.lab.data.network.ConnectionManager
import alex.android.lab.domain.entities.Product
import alex.android.lab.domain.repositories.ProductsRepository
import kotlinx.coroutines.delay
import java.net.UnknownHostException
import javax.inject.Inject

class MockProductsRepositoryImpl @Inject constructor(
    private val connectionManager: ConnectionManager,
    private val mapper: ProductMapper,
    private val db: ProductsDao,
    private val apiService: ApiService,
) : ProductsRepository {

    override suspend fun syncProductsWithApi() {
        if (!checkInternetConnection()) {
            throw UnknownHostException("Please check your internet connection!")
        }
        val dtoProductsList = apiService.getProductsList()
        dtoProductsList.forEach { dtoProduct ->
            val currentDbProduct = db.getProductById(dtoProduct.guid)
            val newProduct = with(mapper) {
                if (currentDbProduct == null) {
                    mapDtoToNewDbModel(dtoProduct)
                } else {
                    mapDtoToCurrentDbModel(dtoProduct, currentDbProduct)
                }
            }
            db.addProduct(newProduct)
        }
    }

    override suspend fun checkInternetConnection(): Boolean {
        for (attempt in 1..3) {
            if (connectionManager.isNetworkAvailable()) {
                return true
            }
            delay(TIMEOUT_RETRY_MILLIS)
        }
        return false
    }

    override suspend fun getProducts(): List<Product> {
        val productList = db.getProducts().map { mapper.mapDbModelToEntity(it) }
        return productList
    }

    override suspend fun getProductsInCart(): List<Product> {
        val productsListInCart = db.getProductsInCart().map { mapper.mapDbModelToEntity(it) }
        return productsListInCart
    }

    override suspend fun getProductById(guid: String): Product {
        val dbModel = checkNotNull(db.getProductById(guid))
        return mapper.mapDbModelToEntity(dbModel)
    }

    override suspend fun updateProductViewCount(guid: String, viewCount: Int) {
        db.updateProductViewCount(guid, viewCount)
    }

    override suspend fun updateProductInCartCount(guid: String, inCartCount: Int) {
        db.updateProductInCartCount(guid, inCartCount)
        if (inCartCount == 0) {
            db.updateProductInCartStatus(guid, isInCart = false)
        } else {
            db.updateProductInCartStatus(guid, isInCart = true)
        }
    }

    override suspend fun updateProductFavoriteStatus(guid: String, isFavorite: Boolean) {
        db.updateProductFavoriteStatus(guid, isFavorite)
    }


    companion object {

        private const val TIMEOUT_RETRY_MILLIS = 5000L
    }
}