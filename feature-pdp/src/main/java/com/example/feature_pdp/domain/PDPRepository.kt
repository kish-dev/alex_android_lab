package com.example.feature_pdp.domain

import com.example.core_model.domain.Product

interface PDPRepository {

    suspend fun getProductById(guid: String): Product

    suspend fun updateProductInCartCount(guid: String, inCartCount: Int)

    suspend fun updateProductFavoriteStatus(guid: String, isFavorite: Boolean)
}