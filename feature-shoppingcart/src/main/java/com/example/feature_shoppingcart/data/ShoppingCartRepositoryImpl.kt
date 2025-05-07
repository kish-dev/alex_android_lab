package com.example.feature_shoppingcart.data

import com.example.core_db_api.DbApi
import com.example.core_model.domain.Product
import com.example.core_network_api.NetworkApi
import com.example.feature_shoppingcart.domain.ShoppingCartRepository
import javax.inject.Inject

class ShoppingCartRepositoryImpl @Inject constructor(
    private val apiService: NetworkApi,
    private val db: DbApi,
) : ShoppingCartRepository {

    override suspend fun syncProductsWithApi() {
        apiService.syncProductsWithApi()
    }

    override suspend fun getProductsInCart(): List<Product> {
        return db.getProductsInCart()
    }

    override suspend fun updateProductViewCount(guid: String, viewCount: Int) {
        db.updateProductViewCount(guid = guid, newViewCount = viewCount)
    }

    override suspend fun updateProductInCartCount(guid: String, inCartCount: Int) {
        db.updateProductInCartCount(guid = guid, inCartCount = inCartCount)
    }
}