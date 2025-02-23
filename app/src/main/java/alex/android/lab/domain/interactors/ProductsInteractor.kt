package alex.android.lab.domain.interactors

import alex.android.lab.domain.entities.Product

interface ProductsInteractor {

    fun getProducts(): List<Product>

    fun getProductById(guid: String): Product
}