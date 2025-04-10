package alex.android.lab.di.modules

import alex.android.lab.di.ViewModelKey
import alex.android.lab.presentation.viewModel.PDPViewModel
import alex.android.lab.presentation.viewModel.ProductsViewModel
import alex.android.lab.presentation.viewModel.ShoppingCartViewModel
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(PDPViewModel::class)
    @Binds
    fun bindPDPViewModel(impl: PDPViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ProductsViewModel::class)
    @Binds
    fun bindProductsViewModel(impl: ProductsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ShoppingCartViewModel::class)
    @Binds
    fun bindShoppingCartViewModel(impl: ShoppingCartViewModel): ViewModel
}