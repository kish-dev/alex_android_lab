package alex.android.lab.app.di

import android.content.Context
import com.example.core_db.di.DbDeps
import com.example.core_db_api.Db
import com.example.core_db_api.DbApi
import com.example.core_navigation.di.NavigationDeps
import com.example.core_navigation_api.FragmentLauncher
import com.example.core_navigation_api.NavigationApi
import com.example.core_network.di.NetworkDeps
import com.example.core_network_api.Network
import com.example.core_network_api.NetworkApi
import com.example.core_utils.di.ApplicationScope
import com.example.feature_pdp.di.FeaturePDPDeps
import com.example.feature_pdp_api.FeaturePDPApi
import com.example.feature_products.di.FeatureProductsDeps
import com.example.feature_shoppingcart.di.FeatureShoppingCartDeps
import com.example.feature_shoppingcart_api.FeatureShoppingCartApi
import dagger.Module
import dagger.Provides

@Module
class AppDepsModule {

    @ApplicationScope
    @Provides
    fun provideDbDeps(
        context: Context,
    ): DbDeps = object : DbDeps {
        override val context: Context = context
    }

    @ApplicationScope
    @Provides
    fun provideNetworkDeps(
        context: Context,
    ): NetworkDeps = object : NetworkDeps {
        override val context: Context = context
    }

    @ApplicationScope
    @Provides
    fun provideFeatureProductsDeps(
        networkApi: NetworkApi,
        dbApi: DbApi,
        navigationApi: NavigationApi,
        pdpApi: FeaturePDPApi,
        shoppingCartApi: FeatureShoppingCartApi,
    ): FeatureProductsDeps = object : FeatureProductsDeps {
        override val apiService: Network = networkApi.getNetwork()
        override val db: Db = dbApi.getDb()
        override val fragmentLauncher: FragmentLauncher = navigationApi.provideFragmentLauncher()
        override val pdpApi: FeaturePDPApi = pdpApi
        override val shoppingCartApi: FeatureShoppingCartApi = shoppingCartApi
    }

    @ApplicationScope
    @Provides
    fun provideFeaturePDPDeps(
        dbApi: DbApi,
    ): FeaturePDPDeps = object : FeaturePDPDeps {
        override val db: Db = dbApi.getDb()
    }

    @ApplicationScope
    @Provides
    fun provideFeatureShoppingCartDeps(
        networkApi: NetworkApi,
        dbApi: DbApi,
        navigationApi: NavigationApi,
        pdpApi: FeaturePDPApi,
    ): FeatureShoppingCartDeps = object : FeatureShoppingCartDeps {
        override val apiService: Network = networkApi.getNetwork()
        override val db: Db = dbApi.getDb()
        override val fragmentLauncher: FragmentLauncher = navigationApi.provideFragmentLauncher()
        override val pdpApi: FeaturePDPApi = pdpApi
    }

    @ApplicationScope
    @Provides
    fun provideNavigationDeps(): NavigationDeps = object : NavigationDeps {}
}