package com.example.core_navigation_impl

import com.example.core_navigation.NavigationProvider
import com.example.feature_pdp.presentation.view.PDPFragment
import com.example.feature_pdp_api.FeaturePDPApi
import com.example.feature_products_api.FeatureProductsApi
import com.example.feature_shoppingcart_api.FeatureShoppingCartApi
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject
import javax.inject.Provider

class AppNavigationProvider @Inject constructor(
    private val router: Router,
    private val featureProductsApi: FeatureProductsApi,
    private val featurePDPApi: Provider<FeaturePDPApi>,
    private val featureShoppingCartProductsApi: Provider<FeatureShoppingCartApi>,
) : NavigationProvider {

    override fun openProductList() {
        val fragment = featureProductsApi.provideFragment()
        router.newRootScreen(FragmentScreen { fragment })
    }

    override fun openProductDetails(productId: String) {
//        val fragment = featurePDPApi.get().provideFragment(productId)
        val fragment = PDPFragment.newInstance(productId)
        router.navigateTo(FragmentScreen { fragment })
    }

    override fun openShoppingCart() {
        val fragment = featureShoppingCartProductsApi.get().provideFragment()
        router.navigateTo(FragmentScreen { fragment })
    }

    override fun goBack() = router.exit()
}