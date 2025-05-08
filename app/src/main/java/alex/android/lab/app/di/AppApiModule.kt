package alex.android.lab.app.di

import alex.android.lab.app.LabApplication
import android.content.Context
import com.example.core_db.di.DbComponentHolder
import com.example.core_db.di.DbDeps
import com.example.core_db_api.DbProvideApi
import com.example.core_navigation.di.NavigationComponentHolder
import com.example.core_navigation.di.NavigationDeps
import com.example.core_navigation_api.NavigationApi
import com.example.core_network.di.NetworkComponentHolder
import com.example.core_network.di.NetworkDeps
import com.example.core_network_api.NetworkProvideApi
import com.example.feature_pdp.di.FeaturePDPComponentHolder
import com.example.feature_pdp.di.FeaturePDPDeps
import com.example.feature_pdp_api.FeaturePDPApi
import com.example.feature_products.di.FeatureProductsComponentHolder
import com.example.feature_products.di.FeatureProductsDeps
import com.example.feature_products_api.FeatureProductsApi
import com.example.feature_shoppingcart.di.FeatureShoppingCartComponentHolder
import com.example.feature_shoppingcart.di.FeatureShoppingCartDeps
import com.example.feature_shoppingcart_api.FeatureShoppingCartApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppApiModule {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return LabApplication.appContext
    }

    @Provides
    fun provideDb(dependencies: DbDeps): DbProvideApi {
        DbComponentHolder.init(dependencies)
        return DbComponentHolder.get()
    }

    @Provides
    fun provideNetwork(dependencies: NetworkDeps): NetworkProvideApi {
        NetworkComponentHolder.init(dependencies)
        return NetworkComponentHolder.get()
    }

    @Provides
    fun provideProductsFeature(dependencies: FeatureProductsDeps): FeatureProductsApi {
        FeatureProductsComponentHolder.init(dependencies)
        return FeatureProductsComponentHolder.get()
    }

    @Provides
    fun providePDPFeature(dependencies: FeaturePDPDeps): FeaturePDPApi {
        FeaturePDPComponentHolder.init(dependencies)
        return FeaturePDPComponentHolder.get()
    }

    @Provides
    fun provideShoppingCartFeature(dependencies: FeatureShoppingCartDeps): FeatureShoppingCartApi {
        FeatureShoppingCartComponentHolder.init(dependencies)
        return FeatureShoppingCartComponentHolder.get()
    }

    @Provides
    fun provideNavigationApi(dependencies: NavigationDeps): NavigationApi {
        NavigationComponentHolder.init(dependencies)
        return NavigationComponentHolder.get()
    }
}