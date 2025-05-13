package com.example.core_db.di

import android.content.Context
import com.example.core_db.DbApiImpl
import com.example.core_db.DbImpl
import com.example.core_db.data.ProductsDao
import com.example.core_db.data.ProductsDatabase
import com.example.core_db_api.Db
import com.example.core_db_api.DbApi
import com.example.core_utils.di.ApplicationScope
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal interface DbModule {

    @ApplicationScope
    @Binds
    fun provideDbApi(impl: DbApiImpl): DbApi

    @ApplicationScope
    @Binds
    fun provideDb(impl: DbImpl): Db

    companion object {

        @ApplicationScope
        @Provides
        fun provideProductsDao(context: Context): ProductsDao =
            ProductsDatabase.getInstance(context).productsDao()
    }
}