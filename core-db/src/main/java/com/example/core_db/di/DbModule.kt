package com.example.core_db.di

import alex.android.lab.data.mapper.ProductMapper
import android.content.Context
import com.example.core_db.DbApiImpl
import com.example.core_db.DbProvideApiImpl
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

    companion object {

        @Singleton
        @Provides
        fun provideDbApi(context: Context): DbApi = DbApiImpl(
            db = ProductsDatabase.getInstance(context).productsDao(),
            mapper = ProductMapper()
        )
    }
}