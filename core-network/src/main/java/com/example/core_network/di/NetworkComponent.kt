package com.example.core_network.di

import com.example.core_network_api.NetworkProvideApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [NetworkDeps::class],
    modules = [NetworkModule::class]
)
internal interface NetworkComponent : NetworkProvideApi {

    companion object {
        fun initAndGet(dependencies: NetworkDeps): NetworkComponent {
            return DaggerNetworkComponent.factory().create(dependencies)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(dependencies: NetworkDeps): NetworkComponent
    }
}