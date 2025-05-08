package com.example.feature_pdp.di

import com.example.core_db_api.Db
import com.example.module_injector.BaseDependencies

interface FeaturePDPDeps : BaseDependencies {
    val db: Db
}