package com.example.core_db.di

import android.content.Context
import com.example.core_db.DbApiImpl
import com.example.core_db.DbImpl
import com.example.core_db.data.ProductsDao
import com.example.core_db.data.ProductsDatabase
import com.example.core_db_api.Db
import com.example.core_db_api.DbApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal interface DbModule {

    @Singleton
    @Binds
    fun provideDbApi(impl: DbApiImpl): DbApi

    @Singleton
    @Binds
    fun provideDb(impl: DbImpl): Db

    companion object {

        @Singleton
        @Provides
        fun provideProductsDao(context: Context): ProductsDao =
            ProductsDatabase.getInstance(context).productsDao()
    }
}