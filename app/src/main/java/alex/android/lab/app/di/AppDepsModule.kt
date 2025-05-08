package alex.android.lab.app.di

import android.content.Context
import com.example.core_db.di.DbDeps
import com.example.core_db_api.DbApi
import com.example.core_db_api.DbProvideApi
import com.example.core_navigation.di.NavigationDeps
import com.example.core_navigation_api.FragmentLauncher
import com.example.core_navigation_api.NavigationApi
import com.example.core_network.di.NetworkDeps
import com.example.core_network_api.NetworkApi
import com.example.core_network_api.NetworkProvideApi
import com.example.feature_pdp.di.FeaturePDPDeps
import com.example.feature_pdp_api.FeaturePDPApi
import com.example.feature_products.di.FeatureProductsDeps
import com.example.feature_shoppingcart.di.FeatureShoppingCartDeps
import com.example.feature_shoppingcart_api.FeatureShoppingCartApi
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
        navigationApi: NavigationApi,
        pdpApi: FeaturePDPApi,
        shoppingCartApi: FeatureShoppingCartApi,
    ): FeatureProductsDeps = object : FeatureProductsDeps {
        override val apiService: NetworkApi = apiService.getNetworkApi()
        override val db: DbApi = db.getDbApi()
        override val fragmentLauncher: FragmentLauncher = navigationApi.provideFragmentLauncher()
        override val pdpApi: FeaturePDPApi = pdpApi
        override val shoppingCartApi: FeatureShoppingCartApi = shoppingCartApi
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
        navigationApi: NavigationApi,
        pdpApi: FeaturePDPApi,
    ): FeatureShoppingCartDeps = object : FeatureShoppingCartDeps {
        override val apiService: NetworkApi = apiService.getNetworkApi()
        override val db: DbApi = db.getDbApi()
        override val fragmentLauncher: FragmentLauncher = navigationApi.provideFragmentLauncher()
        override val pdpApi: FeaturePDPApi = pdpApi
    }

    @Singleton
    @Provides
    fun provideNavigationDeps(): NavigationDeps = object : NavigationDeps {}
}