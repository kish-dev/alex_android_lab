package alex.android.lab.data.di

import alex.android.lab.data.repositoriesImpl.MockProductsRepositoryImpl
import alex.android.lab.data.repositoriesImpl.ProductsInteractorImpl
import alex.android.lab.domain.interactors.ProductsInteractor

object ServiceLocator {

    private var productsInteractor: ProductsInteractor? = null

    fun provideProductsInteractor(): ProductsInteractor {
        return productsInteractor ?: synchronized(this) {
            productsInteractor ?: createProductsInteractor().also { productsInteractor = it }
        }
    }

    private fun createProductsInteractor(): ProductsInteractor {
        return ProductsInteractorImpl(MockProductsRepositoryImpl())
    }
}