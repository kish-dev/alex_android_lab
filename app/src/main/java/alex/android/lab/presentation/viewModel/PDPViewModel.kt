package alex.android.lab.presentation.viewModel

import alex.android.lab.domain.entities.ProductDetailState
import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.presentation.viewObject.ProductInListVO
import alex.android.lab.presentation.viewObject.toVO
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PDPViewModel(private val productsInteractor: ProductsInteractor) : ViewModel() {

    private val _detailProduct = MutableStateFlow<ProductDetailState>(ProductDetailState.Idle)
    val detailProduct: StateFlow<ProductDetailState> = _detailProduct.asStateFlow()

    private val handler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _detailProduct.update {
            ProductDetailState.Error(throwable.message.toString())
        }
    }

    fun getDetailProduct(guid: String) {
        _detailProduct.update {
            ProductDetailState.Loading
        }
        viewModelScope.launch(handler + Dispatchers.IO) {
            val product = productsInteractor.getProductById(guid).toVO()
            _detailProduct.update {
                ProductDetailState.Loaded(product)
            }
        }
    }

    fun changeFavouriteStatus(product: ProductInListVO) {
        viewModelScope.launch(handler + Dispatchers.IO) {
            productsInteractor.updateProductFavoriteStatus(
                guid = product.guid,
                isFavorite = !product.isFavorite
            )
            getDetailProduct(product.guid)
        }
    }
}