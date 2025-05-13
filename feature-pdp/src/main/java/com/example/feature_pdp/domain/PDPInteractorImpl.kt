package com.example.feature_pdp.domain

import com.example.core_model.domain.Product
import javax.inject.Inject

class PDPInteractorImpl @Inject constructor(
    private val pdpRepository: PDPRepository,
) : PDPInteractor {

    override suspend fun getProductById(guid: String): Product {
        return pdpRepository.getProductById(guid = guid)
    }

    override suspend fun updateProductInCartCount(guid: String, inCartCount: Int) {
        pdpRepository.updateProductInCartCount(guid = guid, inCartCount = inCartCount)
    }

    override suspend fun updateProductFavoriteStatus(guid: String, isFavorite: Boolean) {
        pdpRepository.updateProductFavoriteStatus(guid = guid, isFavorite = isFavorite)
    }
}