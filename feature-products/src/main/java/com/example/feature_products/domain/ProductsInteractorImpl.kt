package com.example.feature_products.domain

import com.example.core_model.domain.Product
import javax.inject.Inject

class ProductsInteractorImpl @Inject constructor(
    private val productsRepository: ProductsRepository,
) : ProductsInteractor {

    override suspend fun getProducts(): List<Product> {
        return productsRepository.getProducts()
    }

    override suspend fun updateProductViewCount(guid: String, viewCount: Int) {
        productsRepository.updateProductViewCount(guid = guid, viewCount = viewCount)
    }

    override suspend fun updateProductInCartCount(guid: String, inCartCount: Int) {
        productsRepository.updateProductInCartCount(guid = guid, inCartCount = inCartCount)
    }
}