package com.example.core_network.di

import com.example.core_network_api.NetworkProvideApi
import com.example.module_injector.ComponentHolder

object NetworkComponentHolder : ComponentHolder<NetworkProvideApi, NetworkDeps> {
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

    override fun get(): NetworkProvideApi = getComponent() as NetworkProvideApi

    private fun getComponent(): NetworkComponent {
        checkNotNull(networkComponent) { "NetworkComponent was not initialized!" }
        return networkComponent!!
    }

    override fun reset() {
        networkComponent = null
    }
}