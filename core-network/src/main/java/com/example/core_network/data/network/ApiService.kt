package com.example.core_network.data.network

import com.example.core_model.data.network.ProductInListDTO
import retrofit2.http.GET

interface ApiService {

    @GET("products")
    suspend fun getProductsList(): List<ProductInListDTO>
}