package com.example.core_model.presentation

import com.example.core_model.domain.Product

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