package com.example.feature_shoppingcart.di

import com.example.core_db_api.DbApi
import com.example.core_navigation.NavigationProvider
import com.example.core_network_api.NetworkApi
import com.example.module_injector.BaseDependencies

interface FeatureShoppingCartDeps : BaseDependencies {
    val apiService: NetworkApi
    val db: DbApi
    val navigationProvider: NavigationProvider
}