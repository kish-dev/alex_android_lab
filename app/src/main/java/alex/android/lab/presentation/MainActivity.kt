package alex.android.lab.presentation

import alex.android.lab.R
import alex.android.lab.app.di.ApplicationComponent
import alex.android.lab.databinding.ActivityMainBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.core_navigation_api.FragmentLauncher
import com.example.core_navigation_api.NavigationApi
import com.example.feature_products_api.FeatureProductsApi
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navigator by lazy {
        AppNavigator(this, R.id.fragmentContainer, supportFragmentManager)
    }

    @Inject
    lateinit var navigationApi: NavigationApi

    @Inject
    lateinit var featureProductsApi: FeatureProductsApi

    private val fragmentLauncher: FragmentLauncher by lazy {
        navigationApi.provideFragmentLauncher()
    }

    private val navigatorHolder: NavigatorHolder by lazy {
        navigationApi.provideNavigatorHolder()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ApplicationComponent.get().inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val fragment = featureProductsApi.provideFragment()
            fragmentLauncher.openProductsFragment(fragment)
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
}