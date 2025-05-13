package com.example.feature_shoppingcart.di

import com.example.feature_shoppingcart_api.FeatureShoppingCartApi
import com.example.module_injector.ComponentHolder


object FeatureShoppingCartComponentHolder :
    ComponentHolder<FeatureShoppingCartApi, FeatureShoppingCartDeps> {

    private var featureProductsComponent: FeatureShoppingCartComponent? = null

    override fun init(dependencies: FeatureShoppingCartDeps) {
        if (featureProductsComponent == null) {
            synchronized(FeatureShoppingCartComponentHolder::class.java) {
                if (featureProductsComponent == null) {
                    featureProductsComponent = FeatureShoppingCartComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): FeatureShoppingCartApi = getComponent() as FeatureShoppingCartApi

    internal fun getComponent(): FeatureShoppingCartComponent {
        checkNotNull(featureProductsComponent) { "FeatureProductsComponent was not initialized!" }
        return featureProductsComponent!!
    }

    override fun reset() {
        featureProductsComponent = null
    }
}