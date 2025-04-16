package com.example.core_db_api

import com.example.core_db_api.domain.entities.Product
import com.example.module_injector.BaseAPI

interface DbApi : BaseAPI {

    suspend fun addProduct(product: Product)

    suspend fun getProducts(): List<Product>

    suspend fun getProductsInCart(): List<Product>

    suspend fun getProductById(guid: String): Product

    suspend fun updateProductViewCount(guid: String, newViewCount: Int)

    suspend fun updateProductInCartStatus(guid: String, isInCart: Boolean)

    suspend fun updateProductInCartCount(guid: String, inCartCount: Int)

    suspend fun updateProductFavoriteStatus(guid: String, isFavorite: Boolean)
}