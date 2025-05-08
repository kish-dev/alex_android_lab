package com.example.core_navigation.di

import com.example.core_navigation.FragmentLauncherImpl
import com.example.core_navigation_api.FragmentLauncher
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class NavigationModule {

    @Singleton
    @Binds
    abstract fun bindFragmentLauncher(impl: FragmentLauncherImpl): FragmentLauncher

    companion object {

        private val cicerone: Cicerone<Router> = Cicerone.create()

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
}