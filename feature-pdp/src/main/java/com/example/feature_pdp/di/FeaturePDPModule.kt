package com.example.feature_pdp.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.core_utils.di.FeatureScope
import com.example.core_utils.viewModel.ViewModelKey
import com.example.feature_pdp.data.PDPRepositoryImpl
import com.example.feature_pdp.domain.PDPInteractor
import com.example.feature_pdp.domain.PDPInteractorImpl
import com.example.feature_pdp.domain.PDPRepository
import com.example.feature_pdp.presentation.view.PDPFragment
import com.example.feature_pdp.presentation.viewModel.PDPViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface FeaturePDPModule {

    @IntoMap
    @ViewModelKey(PDPViewModel::class)
    @FeatureScope
    @Binds
    fun bindPDPViewModel(impl: PDPViewModel): ViewModel

    @FeatureScope
    @Binds
    fun bindPDPRepository(impl: PDPRepositoryImpl): PDPRepository

    companion object {

        @FeatureScope
        @Provides
        fun providePDPInteractor(impl: PDPInteractorImpl): PDPInteractor {
            return impl
        }

        // TODO: здесь я также предоставляю фрагмент для api-метода фрагмента, но делаю это очень плохо
        @FeatureScope
        @Provides
        fun providePDPFragment(): (String) -> Fragment = { guid ->
            PDPFragment.newInstance(guid)
        }
    }
}