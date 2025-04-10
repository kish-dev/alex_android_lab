package alex.android.lab.di.modules

import alex.android.lab.data.local.db.ProductsDao
import alex.android.lab.data.local.db.ProductsDatabase
import alex.android.lab.data.network.ApiFactory
import alex.android.lab.data.network.ApiService
import alex.android.lab.data.network.ConnectionManager
import alex.android.lab.di.ApplicationScope
import android.app.Application
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    companion object {

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @Provides
        @ApplicationScope
        fun provideProductsDao(application: Application): ProductsDao {
            return ProductsDatabase.getInstance(application.applicationContext).productsDao()
        }

        @Provides
        @ApplicationScope
        fun provideConnectionManager(application: Application): ConnectionManager {
            return ConnectionManager(application.applicationContext)
        }
    }
}