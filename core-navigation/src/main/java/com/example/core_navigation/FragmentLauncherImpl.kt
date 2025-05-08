package com.example.core_navigation

import androidx.fragment.app.Fragment
import com.example.core_navigation_api.FragmentLauncher
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class FragmentLauncherImpl @Inject constructor() : FragmentLauncher {

    @Inject
    lateinit var router: Router

    //TODO: во все фрагменты поставляется этот "FragmentLauncher", через него запускаются другие фрагменты
    // изначально я хотел, чтобы здесь я получал фрагменты самостоятельно через api мои фич-модулей,
    // но тогда я получаю циклическую зависимость между navigationApi и featureApi.
    // Это решение с навигацией совсем плохое, но это лучшее что я смог придумать чтобы работало.

    override fun openProductsFragment(fragment: Fragment) {
        router.newRootScreen(FragmentScreen { fragment })
    }

    override fun openPDPFragment(fragment: Fragment) {
        router.navigateTo(FragmentScreen { fragment })
    }

    override fun openShoppingCartFragment(fragment: Fragment) {
        router.navigateTo(FragmentScreen { fragment })
    }
}