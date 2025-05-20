package com.example.core_worker.factory

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.core_db_api.Db
import com.example.core_network_api.Network
import com.example.core_worker.workers.GetProductWorker
import com.example.core_worker.workers.GetProductsInCartWorker
import com.example.core_worker.workers.GetProductsListWorker
import com.example.core_worker.workers.SyncProductsWorker
import javax.inject.Inject

class ProductsWorkerFactory @Inject constructor(
    private val apiService: Network,
    private val db: Db,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters,
    ): ListenableWorker? {
        return when (workerClassName) {
            SyncProductsWorker::class.java.name ->
                SyncProductsWorker(appContext, workerParameters, apiService, db)

            GetProductWorker::class.java.name ->
                GetProductWorker(appContext, workerParameters, db)

            GetProductsListWorker::class.java.name ->
                GetProductsListWorker(appContext, workerParameters, db)

            GetProductsInCartWorker::class.java.name ->
                GetProductsInCartWorker(appContext, workerParameters, db)

            else ->
                // Возвращаем null, чтобы WorkManager использовал свой WorkerFactory по умолчанию
                // для других воркеров.
                null
        }
    }
}