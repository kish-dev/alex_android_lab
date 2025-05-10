package com.example.core_model.data.db

import com.example.core_model.domain.Product

fun ProductDbModel.toProduct(): Product {
    return Product(
        guid,
        image,
        name,
        price,
        rating,
        isFavorite,
        isInCart,
        inCartCount,
        viewCount
    )
}