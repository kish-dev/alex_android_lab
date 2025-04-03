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

class ProductsViewModel(private val productsInteractor: ProductsInteractor) : ViewModel() {

    private val _products =
        MutableStateFlow<ProductState<List<ProductInListVO>>>(ProductState.Idle())
    val products: StateFlow<ProductState<List<ProductInListVO>>> = _products.asStateFlow()

    private val handler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _products.update {
                ProductState.Error(throwable.message.toString())
            }
        }
    }

    fun getProducts() {
        _products.update {
            ProductState.Loading()
        }
        viewModelScope.launch(handler + Dispatchers.IO) {
            productsInteractor.syncProductsWithApi()
            val products = productsInteractor.getProducts().map {
                it.toVO()
            }
            _products.update {
                ProductState.Loaded(products)
            }
        }
    }

    fun changeViewCount(product: ProductInListVO) {
        viewModelScope.launch(handler + Dispatchers.IO) {
            productsInteractor.updateProductViewCount(
                guid = product.guid,
                viewCount = product.viewCount + COUNT_ADD_ONE
            )
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

    companion object {

        private const val COUNT_ADD_ONE = 1
    }
}