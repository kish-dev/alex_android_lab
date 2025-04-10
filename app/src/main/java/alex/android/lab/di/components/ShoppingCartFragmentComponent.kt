package alex.android.lab.di.components

import alex.android.lab.di.FeatureDependencies
import alex.android.lab.di.FeatureScope
import alex.android.lab.di.modules.ViewModelModule
import alex.android.lab.presentation.view.ShoppingCartFragment
import dagger.Component

@FeatureScope
@Component(dependencies = [FeatureDependencies::class], modules = [ViewModelModule::class])
interface ShoppingCartFragmentComponent {

    fun inject(fragment: ShoppingCartFragment)

    @Component.Factory
    interface ShoppingCartFragmentComponentFactory {

        fun create(
            dependency: FeatureDependencies,
        ): ShoppingCartFragmentComponent
    }
}