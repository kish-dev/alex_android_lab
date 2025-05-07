package com.example.core_network.data.mapper

import com.example.core_model.data.network.ProductInListDTO
import com.example.core_model.domain.Product
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    fun mapDtoToNewProduct(
        dtoProduct: ProductInListDTO,
    ): Product {
        return Product(
            guid = dtoProduct.guid,
            image = dtoProduct.image,
            name = dtoProduct.name,
            price = dtoProduct.price,
            rating = dtoProduct.rating,
            isFavorite = dtoProduct.isFavorite,
            isInCart = dtoProduct.isInCart,
            inCartCount = 0,
            viewCount = 0
        )
    }

    fun mapDtoToCurrentProduct(
        dtoProduct: ProductInListDTO,
        currentProductFromDb: Product,
    ): Product {
        return Product(
            guid = dtoProduct.guid,
            image = dtoProduct.image,
            name = dtoProduct.name,
            price = dtoProduct.price,
            rating = dtoProduct.rating,
            isFavorite = currentProductFromDb.isFavorite,
            isInCart = currentProductFromDb.isInCart,
            inCartCount = currentProductFromDb.inCartCount,
            viewCount = currentProductFromDb.viewCount
        )
    }

}