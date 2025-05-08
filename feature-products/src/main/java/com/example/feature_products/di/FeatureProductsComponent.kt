package com.example.feature_products.di

import com.example.core_utils.di.FeatureScope
import com.example.feature_products.presentation.view.ProductsFragment
import com.example.feature_products_api.FeatureProductsApi
import dagger.Component

@FeatureScope
@Component(
    dependencies = [FeatureProductsDeps::class],
    modules = [FeatureProductsModule::class]
)
internal interface FeatureProductsComponent : FeatureProductsApi {

    fun inject(productsFragment: ProductsFragment)
//    override fun provideFragment(): Fragment

    companion object {
        fun initAndGet(dependencies: FeatureProductsDeps): FeatureProductsComponent {
            return DaggerFeatureProductsComponent.factory().create(dependencies)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(dependencies: FeatureProductsDeps): FeatureProductsComponent
    }
}