package com.example.feature_pdp.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
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
    @Binds
    fun bindPDPViewModel(impl: PDPViewModel): ViewModel

    @Binds
    fun bindPDPRepository(impl: PDPRepositoryImpl): PDPRepository

    companion object {

        @Provides
        fun providePDPInteractor(impl: PDPInteractorImpl): PDPInteractor {
            return impl
        }
    }
}