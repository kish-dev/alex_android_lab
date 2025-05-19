package com.example.core_network

import com.example.core_network_api.Network
import com.example.core_network_api.NetworkApi
import com.example.core_utils.di.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class NetworkApiImpl @Inject constructor(
    private val network: Network,
) : NetworkApi {

    override fun provideNetwork(): Network {
        return network
    }
}