package com.example.core_worker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.example.core_model.data.db.ProductDbModel
import com.example.core_worker.workers.AlarmReceiver
import com.example.core_worker.workers.GetProductWorker
import com.example.core_worker.workers.GetProductsInCartWorker
import com.example.core_worker.workers.GetProductsListWorker
import com.example.core_worker.workers.SyncProductsWorker
import com.example.core_worker_api.ProductsWorker
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProductsWorkerImpl @Inject constructor() : ProductsWorker {

    @Inject
    lateinit var workManager: WorkManager

    @Inject
    lateinit var context: Context

    override suspend fun getProduct(guid: String): ProductDbModel {
        val input = workDataOf(GetProductWorker.KEY_GUID to guid)

        val request = OneTimeWorkRequestBuilder<GetProductWorker>()
            .setInputData(input)
            .build()
        workManager.enqueue(request)

        return getWorkerResult(request)
    }

    override suspend fun getProductsList(): List<ProductDbModel> {
        val syncProductWorkInfo = syncProducts()

        if (syncProductWorkInfo.state == WorkInfo.State.SUCCEEDED) {
            val request = OneTimeWorkRequestBuilder<GetProductsListWorker>()
                .build()
            workManager.enqueue(request)

            return getWorkerResult(request)
        } else {
            throw RuntimeException("${syncProductWorkInfo.outputData.getString(KEY_ERROR)}")
        }
    }

    override suspend fun getProductsInCart(): List<ProductDbModel> {
        val syncProductWorkInfo = syncProducts()

        if (syncProductWorkInfo.state == WorkInfo.State.SUCCEEDED) {
            val request = OneTimeWorkRequestBuilder<GetProductsInCartWorker>()
                .build()
            workManager.enqueue(request)

            return getWorkerResult(request)
        } else {
            throw RuntimeException("${syncProductWorkInfo.outputData.getString(KEY_ERROR)}")
        }
    }

    private suspend fun syncProducts(): WorkInfo {
        val request = OneTimeWorkRequestBuilder<SyncProductsWorker>().build()
        workManager.enqueue(request)

        val workInfo = workManager.getWorkInfoByIdFlow(request.id)
            .first { checkNotNull(it).state.isFinished }!!
        return workInfo
    }

    override fun schedulePeriodicProductsSync() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + ALARM_INTERVAL_MS,
            ALARM_INTERVAL_MS,
            pendingIntent
        )
    }

    private suspend inline fun <reified T> getWorkerResult(request: WorkRequest): T {
        val workInfo = workManager.getWorkInfoByIdFlow(request.id)
            .first { checkNotNull(it).state.isFinished }!!

        if (workInfo.state == WorkInfo.State.SUCCEEDED) {
            val json = workInfo.outputData.getString(KEY_OUTPUT_DATA)
            val returnType = object : TypeToken<T>() {}.type
            return Gson().fromJson(json, returnType)
        } else {
            throw RuntimeException("${workInfo.outputData.getString(KEY_ERROR)}")
        }
    }

    companion object {

        const val KEY_OUTPUT_DATA = "output data from worker"
        const val KEY_ERROR = "error message from worker"
        private const val ALARM_INTERVAL_MS = 5 * 60 * 1000L
    }
}