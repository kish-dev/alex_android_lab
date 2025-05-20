package com.example.core_worker_api

import com.example.module_injector.BaseAPI

interface WorkerApi : BaseAPI {

    fun provideProductsWorker(): ProductsWorker
}