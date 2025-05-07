package com.example.core_network_api

import com.example.module_injector.BaseAPI

interface NetworkProvideApi : BaseAPI {

    fun getNetworkApi() : NetworkApi
}