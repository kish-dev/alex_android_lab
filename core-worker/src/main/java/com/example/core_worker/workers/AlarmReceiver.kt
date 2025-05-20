package com.example.core_worker.workers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val work = OneTimeWorkRequestBuilder<SyncProductsWorker>().build()
        WorkManager.getInstance(context).enqueue(work)
    }
}