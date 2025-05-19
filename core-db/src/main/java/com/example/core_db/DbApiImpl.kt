package com.example.core_db

import com.example.core_db_api.Db
import com.example.core_db_api.DbApi
import com.example.core_utils.di.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class DbApiImpl @Inject constructor(
    private val db: Db,
) : DbApi {

    override fun provideDb(): Db {
        return db
    }
}