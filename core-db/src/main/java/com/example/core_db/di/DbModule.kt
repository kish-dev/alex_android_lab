package com.example.core_db.di

import alex.android.lab.data.mapper.ProductMapper
import android.content.Context
import com.example.core_db.DbApiImpl
import com.example.core_db.DbProvideApiImpl
import com.example.core_db.data.db.ProductsDao
import com.example.core_db.data.db.ProductsDatabase
import com.example.core_db_api.DbApi
import com.example.core_db_api.DbProvideApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal interface DbModule {

    @Singleton
    @Binds
    fun provideDbProvideApi(impl: DbProvideApiImpl): DbProvideApi

    @Singleton
    @Binds
    fun provideDbApi(impl: DbApiImpl): DbApi

    companion object {

        @Singleton
        @Provides
        fun provideDb(context: Context): ProductsDao =
            ProductsDatabase.getInstance(context).productsDao()

        @Singleton
        @Provides
        fun provideProductMapper(): ProductMapper = ProductMapper()
    }
}