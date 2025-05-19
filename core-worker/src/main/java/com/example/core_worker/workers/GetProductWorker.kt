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

internal class GetProductWorker(
    context: Context,
    params: WorkerParameters,
    private val db: Db,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        try {
            val guid = inputData.getString(KEY_GUID)!!
            val product: ProductDbModel? = db.getProductById(guid)
            val json = Gson().toJson(product)
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

    companion object {

        const val KEY_GUID = "guid"
    }
}