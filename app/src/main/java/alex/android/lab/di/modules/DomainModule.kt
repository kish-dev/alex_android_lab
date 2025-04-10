package alex.android.lab.di.modules

import alex.android.lab.data.repositoriesImpl.MockProductsRepositoryImpl
import alex.android.lab.data.repositoriesImpl.ProductsInteractorImpl
import alex.android.lab.di.ApplicationScope
import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.domain.repositories.ProductsRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    @ApplicationScope
    fun bindProductsRepository(impl: MockProductsRepositoryImpl): ProductsRepository

    @Binds
    @ApplicationScope
    fun bindProductsInteractor(impl: ProductsInteractorImpl): ProductsInteractor
}