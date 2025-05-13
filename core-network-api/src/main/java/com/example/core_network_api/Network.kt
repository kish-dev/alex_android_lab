package com.example.core_network_api

import com.example.core_model.data.network.ProductInListDTO

interface Network {

    suspend fun getProductsList(): List<ProductInListDTO>

    suspend fun checkInternetConnection(): Boolean
}