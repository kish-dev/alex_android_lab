package com.example.core_navigation

interface NavigationProvider {
    fun openProductList()
    fun openProductDetails(productId: String)
    fun openShoppingCart()
    fun goBack()
}