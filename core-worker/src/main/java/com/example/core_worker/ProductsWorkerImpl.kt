package com.example.core_worker

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.example.core_model.data.db.ProductDbModel
import com.example.core_worker.workers.GetProductWorker
import com.example.core_worker.workers.GetProductsInCartWorker
import com.example.core_worker.workers.GetProductsListWorker
import com.example.core_worker.workers.SyncProductsWorker
import com.example.core_worker_api.ProductsWorker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProductsWorkerImpl @Inject constructor() : ProductsWorker {

    @Inject
    lateinit var workManager: WorkManager

    override suspend fun getProduct(guid: String): ProductDbModel {
        val input = workDataOf(GetProductWorker.KEY_GUID to guid)

        val request = OneTimeWorkRequestBuilder<GetProductWorker>()
            .setInputData(input)
            .build()
        workManager.enqueue(request)

        val workInfo = getWorkInfoFromRequest(request)
        if (workInfo.state == WorkInfo.State.SUCCEEDED) {
            val json = workInfo.outputData.getString(KEY_OUTPUT_DATA)
            val type = ProductDbModel::class.java
            return Gson().fromJson(json, type)
        } else {
            throw RuntimeException("${workInfo.outputData.getString(KEY_ERROR)}")
        }
    }

    override suspend fun getProductsList(): List<ProductDbModel> {
        val syncProductsRequest = syncProducts()

        if (syncProductsRequest.state == WorkInfo.State.SUCCEEDED) {
            val request = OneTimeWorkRequestBuilder<GetProductsListWorker>()
                .build()
            workManager.enqueue(request)

            val workInfo = getWorkInfoFromRequest(request)
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                val json = workInfo.outputData.getString(KEY_OUTPUT_DATA)
                val type = object : TypeToken<List<ProductDbModel>>() {}.type
                return Gson().fromJson(json, type)
            } else {
                throw RuntimeException("${workInfo.outputData.getString(KEY_ERROR)}")
            }
        } else {
            throw RuntimeException("${syncProductsRequest.outputData.getString(KEY_ERROR)}")
        }
    }

    override suspend fun getProductsInCart(): List<ProductDbModel> {
        val syncProductsRequest = syncProducts()

        if (syncProductsRequest.state == WorkInfo.State.SUCCEEDED) {
            val request = OneTimeWorkRequestBuilder<GetProductsInCartWorker>()
                .build()
            workManager.enqueue(request)

            val workInfo = getWorkInfoFromRequest(request)
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                val json = workInfo.outputData.getString(KEY_OUTPUT_DATA)
                val type = object : TypeToken<List<ProductDbModel>>() {}.type
                return Gson().fromJson(json, type)
            } else {
                throw RuntimeException("${workInfo.outputData.getString(KEY_ERROR)}")
            }
        } else {
            throw RuntimeException("${syncProductsRequest.outputData.getString(KEY_ERROR)}")
        }
    }

    private suspend fun syncProducts(): WorkInfo {
        val request = OneTimeWorkRequestBuilder<SyncProductsWorker>().build()
        workManager.enqueue(request)

        val workInfo = getWorkInfoFromRequest(request)
        return workInfo
    }

    override fun schedulePeriodicProductsSync() {
        CoroutineScope(Dispatchers.Default).launch {
            repeat(3) { index ->
                val request = PeriodicWorkRequestBuilder<SyncProductsWorker>(
                    15, TimeUnit.MINUTES
                ).build()

                workManager.enqueueUniquePeriodicWork(
                    "product_sync_$index",
                    ExistingPeriodicWorkPolicy.KEEP,
                    request
                )

                delay(TimeUnit.MINUTES.toMillis(5))
            }
        }
    }

    private suspend fun getWorkInfoFromRequest(request: WorkRequest): WorkInfo {
        val workInfo = workManager
            .getWorkInfoByIdFlow(request.id)
            .first { checkNotNull(it).state.isFinished }!!
        return workInfo
    }

    companion object {

        const val KEY_OUTPUT_DATA = "output data from worker"
        const val KEY_ERROR = "error message from worker"
    }
}