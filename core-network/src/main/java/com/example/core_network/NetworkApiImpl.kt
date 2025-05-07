package com.example.core_network

import com.example.core_db_api.DbApi
import com.example.core_network.data.mapper.ProductMapper
import com.example.core_network.data.network.ApiService
import com.example.core_network.data.network.ConnectionManager
import com.example.core_network_api.NetworkApi
import kotlinx.coroutines.delay
import java.net.UnknownHostException
import javax.inject.Inject

class NetworkApiImpl @Inject constructor(
    private val apiService: ApiService,
    private val db: DbApi,
    private val mapper: ProductMapper,
    private val connectionManager: ConnectionManager,
) : NetworkApi {

    override suspend fun syncProductsWithApi() {
        if (!checkInternetConnection()) {
            throw UnknownHostException("Please check your internet connection!")
        }
        val dtoProductsList = apiService.getProductsList()
        dtoProductsList.forEach { dtoProduct ->
            val currentProductFromDb = db.getProductById(dtoProduct.guid)
            val product = with(mapper) {
                if (currentProductFromDb.guid == "null") {
                    mapDtoToNewProduct(dtoProduct)
                } else {
                    mapDtoToCurrentProduct(dtoProduct, currentProductFromDb)
                }
            }
            db.addProduct(product)
        }
    }

    private suspend fun checkInternetConnection(): Boolean {
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