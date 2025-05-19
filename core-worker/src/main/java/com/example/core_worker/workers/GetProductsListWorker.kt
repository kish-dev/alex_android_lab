package com.example.core_worker.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.core_db_api.Db
import com.example.core_model.data.db.ProductDbModel
import com.example.core_worker.ProductsWorkerImpl
import com.google.gson.Gson

internal class GetProductsListWorker(
    context: Context,
    params: WorkerParameters,
    private val db: Db,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        try {
            val products: List<ProductDbModel> = db.getProducts()
            val json = Gson().toJson(products)
            val output = workDataOf(ProductsWorkerImpl.KEY_OUTPUT_DATA to json)
            return Result.success(output)
        } catch (ex: Exception) {
            return Result.failure(
                Data.Builder()
                    .putString(ProductsWorkerImpl.KEY_ERROR, ex.message)
                    .build()
            )
        }

    }
}