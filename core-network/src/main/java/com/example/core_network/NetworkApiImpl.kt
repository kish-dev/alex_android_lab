package com.example.core_network

import com.example.core_network_api.Network
import com.example.core_network_api.NetworkApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkApiImpl @Inject constructor(
    private val network: Network,
) : NetworkApi {

    override fun getNetwork(): Network {
        return network
    }
}