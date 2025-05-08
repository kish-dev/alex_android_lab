package com.example.core_network.di

import android.content.Context
import com.example.core_db_api.Db
import com.example.module_injector.BaseDependencies

interface NetworkDeps : BaseDependencies {
    val context: Context
    val db: Db
}