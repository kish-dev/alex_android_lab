package com.example.core_navigation.di

import com.example.core_navigation.FragmentLauncherImpl
import com.example.core_navigation_api.FragmentLauncher
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule {

    private val cicerone: Cicerone<Router> = Cicerone.create()

//    @Provides
//    @Singleton
//    fun provideNavigationApi(impl: NavigationApiImpl): NavigationApi {
//        return impl
//    }

    @Provides
    @Singleton
    fun provideFragmentLauncher(impl: FragmentLauncherImpl): FragmentLauncher {
        return impl
    }

    @Provides
    @Singleton
    fun provideRouter(): Router {
        return cicerone.router
    }

    @Provides
    @Singleton
    fun provideNavigatorHolder(): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }
}