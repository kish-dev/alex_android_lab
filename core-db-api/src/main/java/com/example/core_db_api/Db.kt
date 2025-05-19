package com.example.core_db_api

import com.example.core_model.data.db.ProductDbModel

interface Db {

    suspend fun addProduct(product: ProductDbModel)

    suspend fun getProducts(): List<ProductDbModel>

    suspend fun getProductsInCart(): List<ProductDbModel>

    suspend fun getProductById(guid: String): ProductDbModel?

    suspend fun updateProductViewCount(guid: String, newViewCount: Int)

    suspend fun updateProductInCartStatus(guid: String, isInCart: Boolean)

    suspend fun updateProductInCartCount(guid: String, inCartCount: Int)

    suspend fun updateProductFavoriteStatus(guid: String, isFavorite: Boolean)
}