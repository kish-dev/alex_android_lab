package com.example.core_db_api

import com.example.module_injector.BaseAPI

interface DbProvideApi : BaseAPI {

    fun getDbApi(): DbApi
}