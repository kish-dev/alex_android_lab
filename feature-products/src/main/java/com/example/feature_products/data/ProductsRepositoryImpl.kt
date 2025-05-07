package com.example.feature_products.data

import com.example.core_db_api.DbApi
import com.example.core_model.domain.Product
import com.example.core_network_api.NetworkApi
import com.example.feature_products.domain.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val apiService: NetworkApi,
    private val db: DbApi,
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