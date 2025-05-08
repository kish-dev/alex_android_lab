package com.example.core_navigation_api

import androidx.fragment.app.Fragment

interface FragmentLauncher {

    fun openProductsFragment(fragment: Fragment)

    fun openPDPFragment(fragment: Fragment)

    fun openShoppingCartFragment(fragment: Fragment)
}