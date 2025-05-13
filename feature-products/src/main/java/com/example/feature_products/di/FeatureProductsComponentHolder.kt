package com.example.feature_products.di

import com.example.feature_products_api.FeatureProductsApi
import com.example.module_injector.ComponentHolder

object FeatureProductsComponentHolder : ComponentHolder<FeatureProductsApi, FeatureProductsDeps> {
    private var featureProductsComponent: FeatureProductsComponent? = null

    override fun init(dependencies: FeatureProductsDeps) {
        if (featureProductsComponent == null) {
            synchronized(FeatureProductsComponentHolder::class.java) {
                if (featureProductsComponent == null) {
                    featureProductsComponent = FeatureProductsComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): FeatureProductsApi = getComponent()

    internal fun getComponent(): FeatureProductsComponent {
        checkNotNull(featureProductsComponent) { "FeatureProductsComponent was not initialized!" }
        return featureProductsComponent!!
    }

    //TODO: во всех ComponentHolder модулей есть метод "reset()", но я не знаю как правильно его использовать,
    // потому что на других примерах видел его вызов только в активити в методах "onPause()",
    // и кажется если много раз открывать фрагменты, то у меня утечка памяти!

    override fun reset() {
        featureProductsComponent = null
    }
}