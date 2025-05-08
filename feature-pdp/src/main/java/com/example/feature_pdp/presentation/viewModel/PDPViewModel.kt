package com.example.feature_pdp.presentation.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.domain.ProductState
import com.example.core_model.presentation.ProductInListVO
import com.example.core_model.presentation.toVO
import com.example.feature_pdp.domain.PDPInteractor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class PDPViewModel @Inject constructor(
    private val pdpInteractor: PDPInteractor,
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
            val product = pdpInteractor.getProductById(guid).toVO()
            _detailProduct.update {
                ProductState.Loaded(product)
            }
        }
    }

    fun changeFavouriteStatus(product: ProductInListVO) {
        viewModelScope.launch(handler + Dispatchers.IO) {
            pdpInteractor.updateProductFavoriteStatus(
                guid = product.guid,
                isFavorite = !product.isFavorite
            )
            getDetailProduct(product.guid)
        }
    }

    fun changeInCartCount(guid: String, inCartCount: Int) {
        viewModelScope.launch(handler + Dispatchers.IO) {
            pdpInteractor.updateProductInCartCount(
                guid = guid,
                inCartCount = inCartCount
            )
        }
    }
}