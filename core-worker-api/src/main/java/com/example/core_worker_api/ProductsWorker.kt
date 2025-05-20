package com.example.core_worker_api

import com.example.core_model.data.db.ProductDbModel

interface ProductsWorker {

    suspend fun getProduct(guid: String): ProductDbModel

    suspend fun getProductsList(): List<ProductDbModel>

    suspend fun getProductsInCart(): List<ProductDbModel>

    fun schedulePeriodicProductsSync()
}