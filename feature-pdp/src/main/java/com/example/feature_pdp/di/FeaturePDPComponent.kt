package com.example.feature_pdp.di

import com.example.core_utils.di.FeatureScope
import com.example.feature_pdp.presentation.view.PDPFragment
import com.example.feature_pdp_api.FeaturePDPApi
import dagger.Component

@FeatureScope
@Component(
    dependencies = [FeaturePDPDeps::class],
    modules = [FeaturePDPModule::class]
)
internal interface FeaturePDPComponent : FeaturePDPApi {

    fun inject(fragment: PDPFragment)

    companion object {
        fun initAndGet(dependencies: FeaturePDPDeps): FeaturePDPComponent {
            return DaggerFeaturePDPComponent.factory().create(dependencies)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(dependencies: FeaturePDPDeps): FeaturePDPComponent
    }
}