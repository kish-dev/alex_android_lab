package com.example.core_network.di

import com.example.core_network_api.NetworkApi
import com.example.module_injector.ComponentHolder

object NetworkComponentHolder : ComponentHolder<NetworkApi, NetworkDeps> {
    private var networkComponent: NetworkComponent? = null

    override fun init(dependencies: NetworkDeps) {
        if (networkComponent == null) {
            synchronized(NetworkComponentHolder::class.java) {
                if (networkComponent == null) {
                    networkComponent = NetworkComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): NetworkApi = getComponent() as NetworkApi

    private fun getComponent(): NetworkComponent {
        checkNotNull(networkComponent) { "NetworkComponent was not initialized!" }
        return networkComponent!!
    }

    override fun reset() {
        networkComponent = null
    }
}