package com.example.core_worker.di

import android.content.Context
import com.example.core_db_api.Db
import com.example.core_network_api.Network
import com.example.module_injector.BaseDependencies

interface WorkerDeps : BaseDependencies {
    val context: Context
    val apiService: Network
    val db: Db
}