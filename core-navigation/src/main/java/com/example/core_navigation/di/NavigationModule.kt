package com.example.core_navigation.di

import com.example.core_navigation.FragmentLauncherImpl
import com.example.core_navigation_api.FragmentLauncher
import com.example.core_utils.di.ApplicationScope
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class NavigationModule {

    @ApplicationScope
    @Binds
    abstract fun bindFragmentLauncher(impl: FragmentLauncherImpl): FragmentLauncher

    companion object {

        private val cicerone: Cicerone<Router> = Cicerone.create()

        @ApplicationScope
        @Provides
        fun provideRouter(): Router {
            return cicerone.router
        }

        @ApplicationScope
        @Provides
        fun provideNavigatorHolder(): NavigatorHolder {
            return cicerone.getNavigatorHolder()
        }
    }
}