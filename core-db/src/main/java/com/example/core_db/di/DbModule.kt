package com.example.core_db.di

import android.content.Context
import com.example.core_db.DbApiImpl
import com.example.core_db.data.db.ProductsDao
import com.example.core_db.data.db.ProductsDatabase
import com.example.core_db_api.DbApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal interface DbModule {

    companion object {

        @Singleton
        @Provides
        fun provideProductsDao(context: Context): ProductsDao {
            return ProductsDatabase.getInstance(context).productsDao()
        }

        //тут не должно быть provides, оно уже есть в AppApiModule
        @Provides
        fun provideDbApi(impl: DbApiImpl): DbApi = impl
    }
}