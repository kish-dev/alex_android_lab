package alex.android.lab.presentation.viewModel

import alex.android.lab.domain.entities.ProductState
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
import javax.inject.Inject

class PDPViewModel @Inject constructor(
    private val productsInteractor: ProductsInteractor,
) : ViewModel() {

    private val _detailProduct =
        MutableStateFlow<ProductState<ProductInListVO>>(ProductState.Idle())
    val detailProduct: StateFlow<ProductState<ProductInListVO>> = _detailProduct.asStateFlow()

    private val handler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _detailProduct.update {
            ProductState.Error(throwable.message.toString())
        }
    }

    fun getDetailProduct(guid: String?) {
        _detailProduct.update {
            ProductState.Loading()
        }
        viewModelScope.launch(handler + Dispatchers.IO) {
            if (guid == null) {
                throw NullPointerException("guid is null for DB search")
            }
            val product = productsInteractor.getProductById(guid).toVO()
            _detailProduct.update {
                ProductState.Loaded(product)
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

    fun changeInCartCount(guid: String, inCartCount: Int) {
        viewModelScope.launch(handler + Dispatchers.IO) {
            productsInteractor.updateProductInCartCount(
                guid = guid,
                inCartCount = inCartCount
            )
        }
    }
}