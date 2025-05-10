package com.example.core_network.di

import android.content.Context
import com.example.core_network.NetworkApiImpl
import com.example.core_network.NetworkImpl
import com.example.core_network.data.ApiFactory
import com.example.core_network.data.ApiService
import com.example.core_network.data.ConnectionManager
import com.example.core_network_api.Network
import com.example.core_network_api.NetworkApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface NetworkModule {

    @Singleton
    @Binds
    fun provideNetworkApi(impl: NetworkApiImpl): NetworkApi

    @Singleton
    @Binds
    fun provideNetwork(impl: NetworkImpl): Network

    companion object {

        @Singleton
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @Singleton
        @Provides
        fun provideConnectionManager(context: Context): ConnectionManager {
            return ConnectionManager(context)
        }
    }
}