package com.example.feature_shoppingcart_api

import androidx.fragment.app.Fragment
import com.example.module_injector.BaseAPI

interface FeatureShoppingCartApi : BaseAPI {

    fun provideFragment(): Fragment
}