package com.example.core_worker.di

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.core_db_api.Db
import com.example.core_network_api.Network
import com.example.core_utils.di.ApplicationScope
import com.example.core_worker.ProductsWorkerImpl
import com.example.core_worker.factory.ProductsWorkerFactory
import com.example.core_worker_api.ProductsWorker
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface WorkerModule {

    @ApplicationScope
    @Binds
    fun provideProductsWorker(impl: ProductsWorkerImpl): ProductsWorker

    companion object {

        @Provides
        fun provideMyWorkerFactory(apiService: Network, db: Db): ProductsWorkerFactory {
            return ProductsWorkerFactory(
                apiService = apiService,
                db = db
            )
        }

        @Provides
        fun provideWorkManager(
            context: Context,
            myWorkerFactory: ProductsWorkerFactory,
        ): WorkManager {
            val workManagerConfiguration: Configuration = Configuration.Builder()
                .setWorkerFactory(myWorkerFactory)
                .build()
            WorkManager.initialize(context, workManagerConfiguration)
            return WorkManager.getInstance(context)
        }
    }
}