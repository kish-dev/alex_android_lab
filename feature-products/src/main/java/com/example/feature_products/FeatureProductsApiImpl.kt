package com.example.feature_products

import androidx.fragment.app.Fragment
import com.example.feature_products.presentation.view.ProductsFragment
import com.example.feature_products_api.FeatureProductsApi
import javax.inject.Inject

class FeatureProductsApiImpl @Inject constructor(
    private val fragment: ProductsFragment
) : FeatureProductsApi {

    override fun provideFragment(): Fragment {
        return fragment
    }
}