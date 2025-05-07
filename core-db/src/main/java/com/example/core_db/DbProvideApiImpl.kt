package com.example.core_db

import com.example.core_db_api.DbApi
import com.example.core_db_api.DbProvideApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbProvideApiImpl @Inject constructor(
    private val dbApi: DbApi,
) : DbProvideApi {

    override fun getDbApi(): DbApi {
        return dbApi
    }
}