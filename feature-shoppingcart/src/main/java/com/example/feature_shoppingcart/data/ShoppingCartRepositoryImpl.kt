package com.example.feature_shoppingcart.data

import com.example.core_db_api.Db
import com.example.core_model.data.db.toProduct
import com.example.core_model.domain.Product
import com.example.core_worker_api.ProductsWorker
import com.example.feature_shoppingcart.domain.ShoppingCartRepository
import javax.inject.Inject

class ShoppingCartRepositoryImpl @Inject constructor(
    private val db: Db,
    private val productsWorker: ProductsWorker,
) : ShoppingCartRepository {

    override suspend fun getProductsInCart(): List<Product> {
        return productsWorker.getProductsInCart().map {
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