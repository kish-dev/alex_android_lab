package com.example.feature_products.data

import com.example.core_db_api.Db
import com.example.core_model.domain.Product
import com.example.core_network_api.Network
import com.example.feature_products.domain.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val apiService: Network,
    private val db: Db,
) : ProductsRepository {
    override suspend fun syncProductsWithApi() {
        apiService.syncProductsWithApi()
    }

    override suspend fun getProducts(): List<Product> {
        return db.getProducts()
    }

    override suspend fun updateProductViewCount(guid: String, viewCount: Int) {
        db.updateProductViewCount(guid = guid, newViewCount = viewCount)
    }

    override suspend fun updateProductInCartCount(guid: String, inCartCount: Int) {
        db.updateProductInCartCount(guid = guid, inCartCount = inCartCount)
    }
}