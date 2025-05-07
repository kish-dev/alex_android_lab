package com.example.feature_shoppingcart.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.core_utils.viewModel.ViewModelKey
import com.example.feature_shoppingcart.data.ShoppingCartRepositoryImpl
import com.example.feature_shoppingcart.domain.ShoppingCartInteractor
import com.example.feature_shoppingcart.domain.ShoppingCartInteractorImpl
import com.example.feature_shoppingcart.domain.ShoppingCartRepository
import com.example.feature_shoppingcart.presentation.view.ShoppingCartFragment
import com.example.feature_shoppingcart.presentation.viewModel.ShoppingCartViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface FeatureShoppingCartModule {

    @IntoMap
    @ViewModelKey(ShoppingCartViewModel::class)
    @Binds
    fun bindShoppingCartViewModel(impl: ShoppingCartViewModel): ViewModel

    @Binds
    fun bindShoppingCartRepository(impl: ShoppingCartRepositoryImpl): ShoppingCartRepository

    companion object {

        @Provides
        fun provideShoppingCartInteractor(impl: ShoppingCartInteractorImpl): ShoppingCartInteractor {
            return impl
        }

        @Provides
        fun provideShoppingCartFragment(): Fragment {
            return ShoppingCartFragment.newInstance()
        }
    }
}