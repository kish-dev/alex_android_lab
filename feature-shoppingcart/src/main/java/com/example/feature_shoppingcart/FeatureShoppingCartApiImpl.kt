package com.example.feature_shoppingcart

import androidx.fragment.app.Fragment
import com.example.feature_shoppingcart.presentation.view.ShoppingCartFragment
import com.example.feature_shoppingcart_api.FeatureShoppingCartApi
import javax.inject.Inject

class FeatureShoppingCartApiImpl @Inject constructor(
    private val fragment: ShoppingCartFragment,
) : FeatureShoppingCartApi {

    override fun provideFragment(): Fragment {
        return fragment
    }
}