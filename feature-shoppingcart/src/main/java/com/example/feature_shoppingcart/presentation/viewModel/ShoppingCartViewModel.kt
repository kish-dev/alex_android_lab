package com.example.feature_shoppingcart.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_model.domain.ProductState
import com.example.core_model.presentation.ProductInListVO
import com.example.core_model.presentation.toVO
import com.example.feature_shoppingcart.domain.ShoppingCartInteractor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingCartViewModel @Inject constructor(
    private val shoppingCartInteractor: ShoppingCartInteractor,
) : ViewModel() {

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
            shoppingCartInteractor.syncProductsWithApi()
            val products = shoppingCartInteractor.getProductsInCart().map {
                it.toVO()
            }
            _products.update {
                ProductState.Loaded(products)
            }
        }
    }

    fun changeViewCount(product: ProductInListVO) {
        viewModelScope.launch(handler + Dispatchers.IO) {
            shoppingCartInteractor.updateProductViewCount(
                guid = product.guid,
                viewCount = product.viewCount + COUNT_ADD_ONE
            )
        }
    }

    fun changeInCartCount(guid: String, inCartCount: Int) {
        viewModelScope.launch(handler + Dispatchers.IO) {
            shoppingCartInteractor.updateProductInCartCount(
                guid = guid,
                inCartCount = inCartCount
            )
        }
    }

    companion object {

        private const val COUNT_ADD_ONE = 1
    }
}