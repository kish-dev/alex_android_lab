package com.example.core_db

import com.example.core_db_api.Db
import com.example.core_db_api.DbApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbApiImpl @Inject constructor(
    private val db: Db,
) : DbApi {

    override fun getDb(): Db {
        return db
    }
}