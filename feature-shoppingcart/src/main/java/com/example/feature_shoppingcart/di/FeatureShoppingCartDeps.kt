package com.example.feature_shoppingcart.di

import com.example.core_db_api.DbApi
import com.example.core_navigation_api.FragmentLauncher
import com.example.core_network_api.NetworkApi
import com.example.feature_pdp_api.FeaturePDPApi
import com.example.module_injector.BaseDependencies

interface FeatureShoppingCartDeps : BaseDependencies {
    val apiService: NetworkApi
    val db: DbApi
    val fragmentLauncher: FragmentLauncher
    val pdpApi: FeaturePDPApi
}