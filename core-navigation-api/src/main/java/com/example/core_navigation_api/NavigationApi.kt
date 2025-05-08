package com.example.core_navigation_api

import com.example.module_injector.BaseAPI
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router

interface NavigationApi : BaseAPI {

    fun provideRouter(): Router

    fun provideNavigatorHolder(): NavigatorHolder

    fun provideFragmentLauncher(): FragmentLauncher
}