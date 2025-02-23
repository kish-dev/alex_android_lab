package alex.android.lab.data.repositoriesImpl

import alex.android.lab.data.mapper.toEntity
import alex.android.lab.domain.entities.Product
import alex.android.lab.domain.repositories.ProductsRepository

class MockProductsRepositoryImpl() : ProductsRepository {
    override fun getProducts(): List<Product> {
        return productInListDTOs.map {
            it.toEntity()
        }
    }

    override fun getProductById(guid: String): Product {
        return productInListDTOs.map {
            it.toEntity()
        }.find {
            it.guid == guid
        } ?: throw RuntimeException("the item was not found in the list!")
    }
}