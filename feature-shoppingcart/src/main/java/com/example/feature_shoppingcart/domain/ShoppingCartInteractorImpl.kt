package com.example.feature_shoppingcart.domain

import com.example.core_model.domain.Product
import javax.inject.Inject

class ShoppingCartInteractorImpl @Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository,
) : ShoppingCartInteractor {

    override suspend fun getProductsInCart(): List<Product> {
        return shoppingCartRepository.getProductsInCart()
    }

    override suspend fun updateProductViewCount(guid: String, viewCount: Int) {
        shoppingCartRepository.updateProductViewCount(guid = guid, viewCount = viewCount)
    }

    override suspend fun updateProductInCartCount(guid: String, inCartCount: Int) {
        shoppingCartRepository.updateProductInCartCount(guid = guid, inCartCount = inCartCount)
    }
}