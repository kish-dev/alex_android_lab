package com.example.core_network

import com.example.core_network_api.NetworkApi
import com.example.core_network_api.NetworkProvideApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkProvideApiImpl @Inject constructor(
    private val networkApi: NetworkApi,
) : NetworkProvideApi {

    override fun getNetworkApi(): NetworkApi {
        return networkApi
    }
}