package com.example.core_model.domain

data class Product(
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