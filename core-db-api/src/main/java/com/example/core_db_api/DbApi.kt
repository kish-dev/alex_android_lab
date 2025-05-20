package com.example.core_db_api

import com.example.module_injector.BaseAPI

interface DbApi : BaseAPI {

    fun provideDb(): Db
}