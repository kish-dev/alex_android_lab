package com.example.core_db

import alex.android.lab.data.mapper.ProductMapper
import com.example.core_db.data.db.ProductsDao
import com.example.core_db_api.DbApi
import com.example.core_model.domain.Product
import javax.inject.Inject

internal class DbApiImpl @Inject constructor(
    private val db: ProductsDao,
    private val mapper: ProductMapper,
) : DbApi {

    override suspend fun addProduct(product: Product) {
        db.addProduct(
            mapper.mapEntityToDbModel(product)
        )
    }

    override suspend fun getProducts(): List<Product> {
        return db.getProducts().map { productDbModel ->
            mapper.mapDbModelToEntity(productDbModel)
        }
    }

    override suspend fun getProductsInCart(): List<Product> {
        return db.getProductsInCart().map { productDbModel ->
            mapper.mapDbModelToEntity(productDbModel)
        }
    }

    override suspend fun getProductById(guid: String): Product {
        val dbModel = checkNotNull(db.getProductById(guid))
        return mapper.mapDbModelToEntity(dbModel)
    }

    override suspend fun updateProductViewCount(guid: String, newViewCount: Int) {
        db.updateProductViewCount(guid, newViewCount)
    }

    override suspend fun updateProductInCartStatus(guid: String, isInCart: Boolean) {
        db.updateProductInCartStatus(guid, isInCart)
    }

    override suspend fun updateProductInCartCount(guid: String, inCartCount: Int) {
        db.updateProductInCartCount(guid, inCartCount)
        if (inCartCount == 0) {
            db.updateProductInCartStatus(guid, isInCart = false)
        } else {
            db.updateProductInCartStatus(guid, isInCart = true)
        }
    }

    override suspend fun updateProductFavoriteStatus(guid: String, isFavorite: Boolean) {
        db.updateProductFavoriteStatus(guid, isFavorite)
    }
}