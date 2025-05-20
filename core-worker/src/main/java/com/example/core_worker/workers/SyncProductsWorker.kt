package com.example.core_worker.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.core_db_api.Db
import com.example.core_model.data.network.toCurrentProductFromDb
import com.example.core_model.data.network.toNewProduct
import com.example.core_model.domain.toDbModel
import com.example.core_network_api.Network
import com.example.core_worker.ProductsWorkerImpl

internal class SyncProductsWorker(
    context: Context,
    params: WorkerParameters,
    private val apiService: Network,
    private val db: Db,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.i("LOG_TAG", "starting doWork() at SyncProductsWorker: ${this.id}")
        try {
            val dtoProductsList = apiService.getProductsList()
            Log.i("LOG_TAG", "dtoProductsList at SyncProductsWorker: $dtoProductsList")
            dtoProductsList.forEach { dtoProduct ->
                val currentProductFromDb = db.getProductById(dtoProduct.guid)

                val product = if (currentProductFromDb == null) {
                    dtoProduct.toNewProduct()
                } else {
                    dtoProduct.toCurrentProductFromDb(currentProductFromDb)
                }
                db.addProduct(product.toDbModel())
            }
        } catch (ex: Exception) {
            return Result.failure(
                Data.Builder()
                    .putString(ProductsWorkerImpl.KEY_ERROR, ex.message)
                    .build()
            )
        }
        return Result.success()
    }
}