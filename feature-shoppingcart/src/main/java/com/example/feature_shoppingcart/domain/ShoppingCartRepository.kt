package com.example.feature_shoppingcart.domain

import com.example.core_model.domain.Product

interface ShoppingCartRepository {

    suspend fun getProductsInCart(): List<Product>

    suspend fun updateProductViewCount(guid: String, viewCount: Int)

    suspend fun updateProductInCartCount(guid: String, inCartCount: Int)
}