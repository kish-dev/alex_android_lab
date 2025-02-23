package alex.android.lab.presentation.viewModel

import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.presentation.viewObject.ProductInListVO
import alex.android.lab.presentation.viewObject.toVO
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PDPViewModel(private val productsInteractor: ProductsInteractor) : ViewModel() {

    private val _detailProductLD = MutableLiveData<ProductInListVO>()
    val detailProductLD: LiveData<ProductInListVO> = _detailProductLD

    fun getDetailProduct(guid: String) {
        _detailProductLD.value = productsInteractor.getProductById(guid).toVO()
    }
}