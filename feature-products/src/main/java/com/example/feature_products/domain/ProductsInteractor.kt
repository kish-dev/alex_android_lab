package com.example.feature_products.domain

import com.example.core_model.domain.Product

interface ProductsInteractor {

    suspend fun syncProductsWithApi()

    suspend fun getProducts(): List<Product>

    suspend fun updateProductViewCount(guid: String, viewCount: Int)

    suspend fun updateProductInCartCount(guid: String, inCartCount: Int)
}