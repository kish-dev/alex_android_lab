package com.example.core_network_api

interface Network {

    suspend fun syncProductsWithApi()
}