package com.example.core_db.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core_model.data.db.ProductDbModel

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(productDbModel: ProductDbModel)

    @Query("SELECT * FROM all_products_list")
    suspend fun getProducts(): List<ProductDbModel>

    @Query("SELECT * FROM all_products_list WHERE isInCart = 1")
    suspend fun getProductsInCart(): List<ProductDbModel>

    @Query("SELECT * FROM all_products_list WHERE guid = :guid LIMIT 1")
    suspend fun getProductById(guid: String): ProductDbModel?

    @Query("UPDATE all_products_list SET viewCount = :newViewCount WHERE guid = :guid")
    suspend fun updateProductViewCount(guid: String, newViewCount: Int)

    @Query("UPDATE all_products_list SET isInCart = :isInCart WHERE guid = :guid")
    suspend fun updateProductInCartStatus(guid: String, isInCart: Boolean)

    @Query("UPDATE all_products_list SET inCartCount = :inCartCount WHERE guid = :guid")
    suspend fun updateProductInCartCount(guid: String, inCartCount: Int)

    @Query("UPDATE all_products_list SET isFavorite = :isFavorite WHERE guid = :guid")
    suspend fun updateProductFavoriteStatus(guid: String, isFavorite: Boolean)
}