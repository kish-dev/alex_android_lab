package alex.android.lab.presentation.viewModel

import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.presentation.viewObject.ProductInListVO
import alex.android.lab.presentation.viewObject.toVO
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductsViewModel(private val productsInteractor: ProductsInteractor) : ViewModel() {

    private val _productLD = MutableLiveData<List<ProductInListVO>>()
    val productLD: LiveData<List<ProductInListVO>> = _productLD

    fun getProducts() {
        _productLD.value = productsInteractor.getProducts().map {
            it.toVO()
        }
    }
}