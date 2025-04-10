package alex.android.lab.di.components

import alex.android.lab.LabApplication
import alex.android.lab.di.ApplicationScope
import alex.android.lab.di.FeatureDependencies
import alex.android.lab.di.modules.DataModule
import alex.android.lab.di.modules.DomainModule
import alex.android.lab.domain.interactors.ProductsInteractor
import android.app.Application
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        DomainModule::class
    ]
)
interface ApplicationComponent : FeatureDependencies {

    override fun productsInteractor(): ProductsInteractor

    fun inject(application: LabApplication)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}