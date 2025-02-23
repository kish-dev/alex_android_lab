package alex.android.lab.data.repositoriesImpl

import alex.android.lab.domain.entities.Product
import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.domain.repositories.ProductsRepository

class ProductsInteractorImpl(private val productsRepository: ProductsRepository) :
    ProductsInteractor {
    override fun getProducts(): List<Product> {
        return productsRepository.getProducts()
    }

    override fun getProductById(guid: String): Product {
        return productsRepository.getProductById(guid)
    }
}