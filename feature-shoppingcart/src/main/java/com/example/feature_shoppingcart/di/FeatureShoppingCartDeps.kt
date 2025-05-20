package com.example.feature_shoppingcart.di

import com.example.core_db_api.Db
import com.example.core_navigation_api.FragmentLauncher
import com.example.core_worker_api.ProductsWorker
import com.example.feature_pdp_api.FeaturePDPApi
import com.example.module_injector.BaseDependencies

interface FeatureShoppingCartDeps : BaseDependencies {
    val db: Db
    val fragmentLauncher: FragmentLauncher
    val pdpApi: FeaturePDPApi
    val productsWorker: ProductsWorker
}