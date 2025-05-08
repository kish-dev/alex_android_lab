package com.example.feature_pdp.di

import com.example.feature_pdp_api.FeaturePDPApi
import com.example.module_injector.ComponentHolder

object FeaturePDPComponentHolder : ComponentHolder<FeaturePDPApi, FeaturePDPDeps> {
    private var featureProductsComponent: FeaturePDPComponent? = null

    override fun init(dependencies: FeaturePDPDeps) {
        if (featureProductsComponent == null) {
            synchronized(FeaturePDPComponentHolder::class.java) {
                if (featureProductsComponent == null) {
                    featureProductsComponent = FeaturePDPComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): FeaturePDPApi = getComponent() as FeaturePDPApi

    internal fun getComponent(): FeaturePDPComponent {
        checkNotNull(featureProductsComponent) { "FeatureProductsComponent was not initialized!" }
        return featureProductsComponent!!
    }

    override fun reset() {
        featureProductsComponent = null
    }
}