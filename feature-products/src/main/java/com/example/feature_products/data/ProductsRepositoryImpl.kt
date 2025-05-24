package com.example.feature_products.data

import com.example.core_db_api.Db
import com.example.core_model.data.db.toProduct
import com.example.core_model.domain.Product
import com.example.core_utils.di.DispatcherProvider
import com.example.core_worker_api.ProductsWorker
import com.example.feature_products.domain.ProductsRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val db: Db,
    private val productsWorker: ProductsWorker,
    private val dispatcher: DispatcherProvider,
) : ProductsRepository {

    override suspend fun getProducts(): List<Product> {
        return withContext(dispatcher.io) {
            productsWorker.getProductsList().map {
                it.toProduct()
            }
        }
    }

    override suspend fun updateProductViewCount(guid: String, viewCount: Int) {
        withContext(dispatcher.io) {
            db.updateProductViewCount(
                guid = guid,
                newViewCount = viewCount
            )
        }
    }

    override suspend fun updateProductInCartCount(guid: String, inCartCount: Int) {
        withContext(dispatcher.io) {
            db.updateProductInCartCount(
                guid = guid,
                inCartCount = inCartCount
            )
        }
    }
}