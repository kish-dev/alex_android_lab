package com.example.core_model.data.network

import com.example.core_model.data.db.ProductDbModel
import com.example.core_model.domain.Product

fun ProductInListDTO.toNewProduct(): Product {
    return Product(
        guid = guid,
        image = image,
        name = name,
        price = price,
        rating = rating,
        isFavorite = isFavorite,
        isInCart = isInCart,
        inCartCount = 0,
        viewCount = 0
    )
}

fun ProductInListDTO.toCurrentProductFromDb(currentProductFromDb: ProductDbModel): Product {
    return Product(
        guid = guid,
        image = image,
        name = name,
        price = price,
        rating = rating,
        isFavorite = currentProductFromDb.isFavorite,
        isInCart = currentProductFromDb.isInCart,
        inCartCount = currentProductFromDb.inCartCount,
        viewCount = currentProductFromDb.viewCount
    )
}
