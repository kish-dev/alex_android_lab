package com.example.core_db.di

import android.content.Context
import com.example.module_injector.BaseDependencies

interface DbDeps : BaseDependencies {
    val context: Context
}