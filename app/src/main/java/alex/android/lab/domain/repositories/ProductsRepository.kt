package alex.android.lab.domain.repositories

import alex.android.lab.domain.entities.Product

interface ProductsRepository {

    fun getProducts(): List<Product>

    fun getProductById(guid: String): Product
}