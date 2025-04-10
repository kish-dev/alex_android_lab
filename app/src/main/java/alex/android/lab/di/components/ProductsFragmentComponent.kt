package alex.android.lab.di.components

import alex.android.lab.di.FeatureDependencies
import alex.android.lab.di.FeatureScope
import alex.android.lab.di.modules.ViewModelModule
import alex.android.lab.presentation.view.ProductsFragment
import dagger.Component

@FeatureScope
@Component(dependencies = [FeatureDependencies::class], modules = [ViewModelModule::class])
interface ProductsFragmentComponent {

    fun inject(fragment: ProductsFragment)

    @Component.Factory
    interface ProductsFragmentComponentFactory {

        fun create(
            dependency: FeatureDependencies,
        ): ProductsFragmentComponent
    }
}