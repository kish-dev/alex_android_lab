package com.example.feature_products.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.core_utils.di.FeatureScope
import com.example.core_utils.viewModel.ViewModelKey
import com.example.feature_products.data.ProductsRepositoryImpl
import com.example.feature_products.domain.ProductsInteractor
import com.example.feature_products.domain.ProductsInteractorImpl
import com.example.feature_products.domain.ProductsRepository
import com.example.feature_products.presentation.view.ProductsFragment
import com.example.feature_products.presentation.viewModel.ProductsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface FeatureProductsModule {

    @IntoMap
    @ViewModelKey(ProductsViewModel::class)
    @FeatureScope
    @Binds
    fun bindProductsViewModel(impl: ProductsViewModel): ViewModel

    @FeatureScope
    @Binds
    fun bindProductsRepository(impl: ProductsRepositoryImpl): ProductsRepository

    companion object {

        @FeatureScope
        @Provides
        fun provideProductsInteractor(impl: ProductsInteractorImpl): ProductsInteractor {
            return impl
        }

        //TODO: Здесь я предоставляю фрагмент для моего api-метода по получению фрагмента
        @FeatureScope
        @Provides
        fun provideProductsFragment(): Fragment {
            return ProductsFragment.newInstance()
        }
    }
}