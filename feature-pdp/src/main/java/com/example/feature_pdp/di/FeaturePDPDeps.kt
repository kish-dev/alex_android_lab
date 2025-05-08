package com.example.feature_pdp.di

import com.example.core_db_api.DbApi
import com.example.module_injector.BaseDependencies

interface FeaturePDPDeps : BaseDependencies {
    val db: DbApi
}