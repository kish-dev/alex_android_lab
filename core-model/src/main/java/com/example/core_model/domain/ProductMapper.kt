package com.example.core_model.domain

import com.example.core_model.data.db.ProductDbModel
import com.example.core_model.presentation.ProductInListVO

fun Product.toDbModel(): ProductDbModel {
    return ProductDbModel(
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

fun Product.toVO(): ProductInListVO {
    return ProductInListVO(
        guid,
        image,
        name,
        "$price ₽",
        rating,
        isFavorite,
        isInCart,
        inCartCount,
        viewCount
    )
}