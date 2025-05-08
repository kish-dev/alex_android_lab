package com.example.core_db_api

import com.example.core_model.domain.Product

interface Db {

    suspend fun addProduct(product: Product)

    suspend fun getProducts(): List<Product>

    suspend fun getProductsInCart(): List<Product>

    suspend fun getProductById(guid: String): Product

    suspend fun updateProductViewCount(guid: String, newViewCount: Int)

    suspend fun updateProductInCartStatus(guid: String, isInCart: Boolean)

    suspend fun updateProductInCartCount(guid: String, inCartCount: Int)

    suspend fun updateProductFavoriteStatus(guid: String, isFavorite: Boolean)
}