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
import com.example.core_worker.di.WorkerDeps
import com.example.core_worker_api.ProductsWorker
import com.example.core_worker_api.WorkerApi
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
        dbApi: DbApi,
        navigationApi: NavigationApi,
        pdpApi: FeaturePDPApi,
        shoppingCartApi: FeatureShoppingCartApi,
        workerApi: WorkerApi,
    ): FeatureProductsDeps = object : FeatureProductsDeps {
        override val db: Db = dbApi.provideDb()
        override val fragmentLauncher: FragmentLauncher = navigationApi.provideFragmentLauncher()
        override val pdpApi: FeaturePDPApi = pdpApi
        override val shoppingCartApi: FeatureShoppingCartApi = shoppingCartApi
        override val productsWorker: ProductsWorker = workerApi.provideProductsWorker()
    }

    @ApplicationScope
    @Provides
    fun provideFeaturePDPDeps(
        dbApi: DbApi,
        workerApi: WorkerApi,
    ): FeaturePDPDeps = object : FeaturePDPDeps {
        override val db: Db = dbApi.provideDb()
        override val productsWorker: ProductsWorker = workerApi.provideProductsWorker()
    }

    @ApplicationScope
    @Provides
    fun provideFeatureShoppingCartDeps(
        dbApi: DbApi,
        navigationApi: NavigationApi,
        pdpApi: FeaturePDPApi,
        workerApi: WorkerApi,
    ): FeatureShoppingCartDeps = object : FeatureShoppingCartDeps {
        override val db: Db = dbApi.provideDb()
        override val fragmentLauncher: FragmentLauncher = navigationApi.provideFragmentLauncher()
        override val pdpApi: FeaturePDPApi = pdpApi
        override val productsWorker: ProductsWorker = workerApi.provideProductsWorker()
    }

    @ApplicationScope
    @Provides
    fun provideNavigationDeps(): NavigationDeps = object : NavigationDeps {}

    @ApplicationScope
    @Provides
    fun provideWorkerDeps(
        context: Context,
        networkApi: NetworkApi,
        dbApi: DbApi,
    ): WorkerDeps = object : WorkerDeps {
        override val context: Context = context
        override val apiService: Network = networkApi.provideNetwork()
        override val db: Db = dbApi.provideDb()
    }
}