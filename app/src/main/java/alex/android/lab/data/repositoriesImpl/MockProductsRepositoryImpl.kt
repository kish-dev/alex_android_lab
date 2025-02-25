package alex.android.lab.data.repositoriesImpl

import alex.android.lab.data.di.ServiceLocator
import alex.android.lab.domain.entities.Product
import alex.android.lab.domain.repositories.ProductsRepository
import android.content.Context
import java.net.UnknownHostException

class MockProductsRepositoryImpl(private val context: Context) : ProductsRepository {

    private val apiService = ServiceLocator.provideApiService()
    private val db = ServiceLocator.provideProductsDatabase(context)
    private val mapper = ServiceLocator.provideProductMapper()
    private val connectionManager = ServiceLocator.provideConnectionManager(context)

    override suspend fun syncProductsWithApi() {
        if (!connectionManager.isNetworkAvailable()) {
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

    override suspend fun getProducts(): List<Product> {
        val productList = db.getProducts().map { mapper.mapDbModelToEntity(it) }
        return productList
    }

    override suspend fun getProductById(guid: String): Product {
        val dbModel = checkNotNull(db.getProductById(guid))
        return mapper.mapDbModelToEntity(dbModel)
    }

    override suspend fun updateProductViewCount(guid: String, viewCount: Int) {
        db.updateProductViewCount(guid, viewCount)
    }

    override suspend fun updateProductInCartStatus(guid: String, isInCart: Boolean) {
        db.updateProductInCartStatus(guid, isInCart)
    }

    override suspend fun updateProductFavoriteStatus(guid: String, isFavorite: Boolean) {
        db.updateProductFavoriteStatus(guid, isFavorite)
    }
}