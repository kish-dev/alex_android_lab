package com.example.core_network.di

import android.content.Context
import com.example.core_network.NetworkApiImpl
import com.example.core_network.NetworkProvideApiImpl
import com.example.core_network.data.network.ApiFactory
import com.example.core_network.data.network.ApiService
import com.example.core_network.data.network.ConnectionManager
import com.example.core_network_api.NetworkApi
import com.example.core_network_api.NetworkProvideApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface NetworkModule {

    @Singleton
    @Binds
    fun provideNetworkProvideApi(impl: NetworkProvideApiImpl): NetworkProvideApi

    @Singleton
    @Binds
    fun provideNetworkApi(impl: NetworkApiImpl): NetworkApi

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