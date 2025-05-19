package com.example.feature_products.data

import com.example.core_db_api.Db
import com.example.core_model.data.db.toProduct
import com.example.core_model.domain.Product
import com.example.core_worker_api.ProductsWorker
import com.example.feature_products.domain.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val db: Db,
    private val productsWorker: ProductsWorker,
) : ProductsRepository {

    override suspend fun getProducts(): List<Product> {
        return productsWorker.getProductsList().map {
            it.toProduct()
        }
    }

    override suspend fun updateProductViewCount(guid: String, viewCount: Int) {
        db.updateProductViewCount(guid = guid, newViewCount = viewCount)
    }

    override suspend fun updateProductInCartCount(guid: String, inCartCount: Int) {
        db.updateProductInCartCount(guid = guid, inCartCount = inCartCount)
    }
}