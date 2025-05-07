package com.example.feature_products_api

import androidx.fragment.app.Fragment
import com.example.module_injector.BaseAPI

interface FeatureProductsApi : BaseAPI {

    fun provideFragment(): Fragment
}