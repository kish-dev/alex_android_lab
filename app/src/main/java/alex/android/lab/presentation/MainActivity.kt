package alex.android.lab.presentation

import alex.android.lab.R
import alex.android.lab.app.di.ApplicationComponent
import alex.android.lab.databinding.ActivityMainBinding
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.core_navigation.NavigationProvider
import com.example.feature_pdp.presentation.view.PDPFragment
import com.example.feature_pdp_api.FeaturePDPApi
import com.example.feature_products_api.FeatureProductsApi
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navigator = AppNavigator(this, R.id.fragmentContainer)

//    @Inject
//    lateinit var navigationProvider: NavigationProvider
    @Inject
    lateinit var featureProductsApi: FeatureProductsApi

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        ApplicationComponent.get().inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
//            navigationProvider.openProductList()
            val fragment = featureProductsApi.provideFragment()
            router.navigateTo(FragmentScreen { fragment })
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        } else {
            router.exit()
//            navigationProvider.goBack()
        }
    }
}