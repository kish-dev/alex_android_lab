package com.example.core_model.presentation

data class ProductInListVO(
    val guid: String,
    val image: String,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val inCartCount: Int,
    val viewCount: Int,
)
