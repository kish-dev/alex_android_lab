package com.example.core_network.di

import android.content.Context
import com.example.core_network.NetworkApiImpl
import com.example.core_network.NetworkImpl
import com.example.core_network.data.ApiFactory
import com.example.core_network.data.ApiService
import com.example.core_network.data.ConnectionManager
import com.example.core_network_api.Network
import com.example.core_network_api.NetworkApi
import com.example.core_utils.di.ApplicationScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface NetworkModule {

    @ApplicationScope
    @Binds
    fun provideNetworkApi(impl: NetworkApiImpl): NetworkApi

    @ApplicationScope
    @Binds
    fun provideNetwork(impl: NetworkImpl): Network

    companion object {

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @ApplicationScope
        @Provides
        fun provideConnectionManager(context: Context): ConnectionManager {
            return ConnectionManager(context)
        }
    }
}