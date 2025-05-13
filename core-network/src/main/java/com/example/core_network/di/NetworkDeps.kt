package com.example.core_network.di

import android.content.Context
import com.example.module_injector.BaseDependencies

interface NetworkDeps : BaseDependencies {
    val context: Context
}