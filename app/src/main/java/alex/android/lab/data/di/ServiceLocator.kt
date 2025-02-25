package alex.android.lab.data.di

import alex.android.lab.data.local.db.ProductsDao
import alex.android.lab.data.local.db.ProductsDatabase
import alex.android.lab.data.mapper.ProductMapper
import alex.android.lab.data.network.ApiFactory
import alex.android.lab.data.network.ApiService
import alex.android.lab.data.network.ConnectionManager
import alex.android.lab.data.repositoriesImpl.MockProductsRepositoryImpl
import alex.android.lab.data.repositoriesImpl.ProductsInteractorImpl
import alex.android.lab.domain.interactors.ProductsInteractor
import android.annotation.SuppressLint
import android.content.Context

object ServiceLocator {

    private var productsInteractor: ProductsInteractor? = null
    private var apiService: ApiService? = null
    private var db: ProductsDao? = null
    private var productMapper: ProductMapper? = null

    @SuppressLint("StaticFieldLeak")
    private var connectionManager: ConnectionManager? = null

    fun provideProductsInteractor(context: Context): ProductsInteractor {
        return productsInteractor ?: synchronized(this) {
            productsInteractor ?: createProductsInteractor(context).also { productsInteractor = it }
        }
    }

    fun provideApiService(): ApiService {
        return apiService ?: synchronized(this) {
            apiService ?: createApiService().also { apiService = it }
        }
    }

    fun provideProductsDatabase(context: Context): ProductsDao {
        return db ?: synchronized(this) {
            db ?: createProductsDatabase(context).also { db = it }
        }
    }

    fun provideProductMapper(): ProductMapper {
        return productMapper ?: synchronized(this) {
            productMapper ?: createProductMapper().also { productMapper = it }
        }
    }

    fun provideConnectionManager(context: Context): ConnectionManager {
        return connectionManager ?: synchronized(this) {
            connectionManager ?: createConnectionManager(context).also { connectionManager = it }
        }
    }

    private fun createProductsInteractor(context: Context): ProductsInteractor {
        return ProductsInteractorImpl(MockProductsRepositoryImpl(context))
    }

    private fun createApiService(): ApiService {
        return ApiFactory.apiService
    }

    private fun createProductsDatabase(context: Context): ProductsDao {
        return ProductsDatabase.getInstance(context).productsDao()
    }

    private fun createProductMapper(): ProductMapper {
        return ProductMapper()
    }

    private fun createConnectionManager(context: Context): ConnectionManager {
        return ConnectionManager(context.applicationContext)
    }
}