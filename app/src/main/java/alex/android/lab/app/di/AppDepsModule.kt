package alex.android.lab.app.di

import android.content.Context
import com.example.core_db.di.DbDeps
import com.example.core_db_api.DbApi
import com.example.core_db_api.DbProvideApi
import com.example.core_navigation.NavigationProvider
import com.example.core_network.di.NetworkDeps
import com.example.core_network_api.NetworkApi
import com.example.core_network_api.NetworkProvideApi
import com.example.feature_pdp.di.FeaturePDPDeps
import com.example.feature_products.di.FeatureProductsDeps
import com.example.feature_shoppingcart.di.FeatureShoppingCartDeps
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppDepsModule {

    @Singleton
    @Provides
    fun provideDbDeps(
        context: Context,
    ): DbDeps = object : DbDeps {
        override val context: Context = context
    }

    @Singleton
    @Provides
    fun provideNetworkDeps(
        context: Context,
        db: DbProvideApi,
    ): NetworkDeps = object : NetworkDeps {
        override val context: Context = context
        override val db: DbApi = db.getDbApi()
    }

    @Singleton
    @Provides
    fun provideFeatureProductsDeps(
        apiService: NetworkProvideApi,
        db: DbProvideApi,
        navigationProvider: NavigationProvider,
    ): FeatureProductsDeps = object : FeatureProductsDeps {
        override val apiService: NetworkApi = apiService.getNetworkApi()
        override val db: DbApi = db.getDbApi()
        override val navigationProvider: NavigationProvider = navigationProvider
    }

    @Singleton
    @Provides
    fun provideFeaturePDPDeps(
        db: DbProvideApi,
    ): FeaturePDPDeps = object : FeaturePDPDeps {
        override val db: DbApi = db.getDbApi()
    }

    @Singleton
    @Provides
    fun provideFeatureShoppingCartDeps(
        apiService: NetworkProvideApi,
        db: DbProvideApi,
        navigationProvider: NavigationProvider,
    ): FeatureShoppingCartDeps = object : FeatureShoppingCartDeps {
        override val apiService: NetworkApi = apiService.getNetworkApi()
        override val db: DbApi = db.getDbApi()
        override val navigationProvider: NavigationProvider = navigationProvider
    }
}