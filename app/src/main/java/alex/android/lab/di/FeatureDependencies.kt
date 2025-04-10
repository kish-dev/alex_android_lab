package alex.android.lab.di

import alex.android.lab.domain.interactors.ProductsInteractor

interface FeatureDependencies {

    fun productsInteractor(): ProductsInteractor
}