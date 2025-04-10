package alex.android.lab.di.components

import alex.android.lab.di.FeatureDependencies
import alex.android.lab.di.FeatureScope
import alex.android.lab.di.modules.ViewModelModule
import alex.android.lab.presentation.view.PDPFragment
import dagger.Component

@FeatureScope
@Component(dependencies = [FeatureDependencies::class], modules = [ViewModelModule::class])
interface PDPFragmentComponent {

    fun inject(fragment: PDPFragment)

    @Component.Factory
    interface PDPFragmentComponentFactory {

        fun create(
            dependency: FeatureDependencies,
        ): PDPFragmentComponent
    }
}