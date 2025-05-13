package com.example.core_network

import com.example.core_model.data.network.ProductInListDTO
import com.example.core_network.data.ApiService
import com.example.core_network.data.ConnectionManager
import com.example.core_network_api.Network
import kotlinx.coroutines.delay
import java.net.UnknownHostException
import javax.inject.Inject

class NetworkImpl @Inject constructor(
    private val apiService: ApiService,
    private val connectionManager: ConnectionManager,
) : Network {

    override suspend fun getProductsList(): List<ProductInListDTO> {
        if (!checkInternetConnection()) {
            throw UnknownHostException("Please check your internet connection!")
        }
        return apiService.getProductsList()
    }

    override suspend fun checkInternetConnection(): Boolean {
        for (attempt in 1..3) {
            if (connectionManager.isNetworkAvailable()) {
                return true
            }
            delay(TIMEOUT_RETRY_MILLIS)
        }
        return false
    }

    companion object {

        private const val TIMEOUT_RETRY_MILLIS = 5000L
    }
}