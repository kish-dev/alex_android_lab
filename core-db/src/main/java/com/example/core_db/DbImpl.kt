package com.example.core_db

import alex.android.lab.data.mapper.ProductMapper
import com.example.core_db.data.db.ProductsDao
import com.example.core_db_api.Db
import com.example.core_model.domain.Product
import javax.inject.Inject

internal class DbImpl @Inject constructor(
    private val productsDao: ProductsDao,
    private val mapper: ProductMapper,
) : Db {

    override suspend fun addProduct(product: Product) {
        productsDao.addProduct(
            mapper.mapEntityToDbModel(product)
        )
    }

    override suspend fun getProducts(): List<Product> {
        return productsDao.getProducts().map { productDbModel ->
            mapper.mapDbModelToEntity(productDbModel)
        }
    }

    override suspend fun getProductsInCart(): List<Product> {
        return productsDao.getProductsInCart().map { productDbModel ->
            mapper.mapDbModelToEntity(productDbModel)
        }
    }

    override suspend fun getProductById(guid: String): Product {
        val dbModel = checkNotNull(productsDao.getProductById(guid))
        return mapper.mapDbModelToEntity(dbModel)
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