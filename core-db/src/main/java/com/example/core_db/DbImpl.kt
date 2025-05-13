package com.example.core_db

import com.example.core_db.data.ProductsDao
import com.example.core_db_api.Db
import com.example.core_model.data.db.ProductDbModel
import javax.inject.Inject

internal class DbImpl @Inject constructor(
    private val productsDao: ProductsDao,
) : Db {

    override suspend fun addProduct(product: ProductDbModel) {
        productsDao.addProduct(product)
    }

    override suspend fun getProducts(): List<ProductDbModel> {
        return productsDao.getProducts()
    }

    override suspend fun getProductsInCart(): List<ProductDbModel> {
        return productsDao.getProductsInCart()
    }

    override suspend fun getProductById(guid: String): ProductDbModel {
        return checkNotNull(productsDao.getProductById(guid))
    }

    override suspend fun updateProductViewCount(guid: String, newViewCount: Int) {
        productsDao.updateProductViewCount(guid, newViewCount)
    }

    override suspend fun updateProductInCartStatus(guid: String, isInCart: Boolean) {
        productsDao.updateProductInCartStatus(guid, isInCart)
    }

    override suspend fun updateProductInCartCount(guid: String, inCartCount: Int) {
        productsDao.updateProductInCartCount(guid, inCartCount)
        if (inCartCount == 0) {
            productsDao.updateProductInCartStatus(guid, isInCart = false)
        } else {
            productsDao.updateProductInCartStatus(guid, isInCart = true)
        }
    }

    override suspend fun updateProductFavoriteStatus(guid: String, isFavorite: Boolean) {
        productsDao.updateProductFavoriteStatus(guid, isFavorite)
    }
}