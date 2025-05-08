package com.example.feature_pdp.data

import com.example.core_db_api.Db
import com.example.core_model.domain.Product
import com.example.feature_pdp.domain.PDPRepository
import javax.inject.Inject

class PDPRepositoryImpl @Inject constructor(
    private val db: Db,
) : PDPRepository {

    override suspend fun getProductById(guid: String): Product {
        return db.getProductById(guid = guid)
    }

    override suspend fun updateProductInCartCount(guid: String, inCartCount: Int) {
        db.updateProductInCartCount(guid = guid, inCartCount = inCartCount)
    }

    override suspend fun updateProductFavoriteStatus(guid: String, isFavorite: Boolean) {
        db.updateProductFavoriteStatus(guid = guid, isFavorite = isFavorite)
    }
}