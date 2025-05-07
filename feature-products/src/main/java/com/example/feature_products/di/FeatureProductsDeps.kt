package com.example.feature_products.di

import com.example.core_db_api.DbApi
import com.example.core_navigation.NavigationProvider
import com.example.core_network_api.NetworkApi
import com.example.module_injector.BaseDependencies

interface FeatureProductsDeps : BaseDependencies {
    val apiService: NetworkApi
    val db: DbApi
    val navigationProvider: NavigationProvider
}