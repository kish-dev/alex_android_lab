package com.example.feature_shoppingcart.di

import com.example.core_utils.di.FeatureScope
import com.example.feature_shoppingcart.presentation.view.ShoppingCartFragment
import com.example.feature_shoppingcart_api.FeatureShoppingCartApi
import dagger.Component

@FeatureScope
@Component(
    dependencies = [FeatureShoppingCartDeps::class],
    modules = [FeatureShoppingCartModule::class]
)
internal interface FeatureShoppingCartComponent : FeatureShoppingCartApi {

    fun inject(fragment: ShoppingCartFragment)

    companion object {
        fun initAndGet(dependencies: FeatureShoppingCartDeps): FeatureShoppingCartComponent {
            return DaggerFeatureShoppingCartComponent.factory().create(dependencies)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(dependencies: FeatureShoppingCartDeps): FeatureShoppingCartComponent
    }
}