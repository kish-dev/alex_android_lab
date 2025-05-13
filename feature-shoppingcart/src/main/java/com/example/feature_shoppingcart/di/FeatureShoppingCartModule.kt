package com.example.feature_shoppingcart.di

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.core_utils.di.FeatureScope
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
    @FeatureScope
    @Binds
    fun bindShoppingCartViewModel(impl: ShoppingCartViewModel): ViewModel

    @FeatureScope
    @Binds
    fun bindShoppingCartRepository(impl: ShoppingCartRepositoryImpl): ShoppingCartRepository

    companion object {

        @FeatureScope
        @Provides
        fun provideShoppingCartInteractor(impl: ShoppingCartInteractorImpl): ShoppingCartInteractor {
            return impl
        }

        @FeatureScope
        @Provides
        fun provideShoppingCartFragment(): Fragment {
            Log.d("LOG_TAG", "provideShoppingCartFragment in FeatureShoppingCartModule")
            return ShoppingCartFragment.newInstance()
        }
    }
}