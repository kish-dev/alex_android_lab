package com.example.core_navigation.di

import com.example.core_navigation_api.FragmentLauncher
import com.example.core_navigation_api.NavigationApi
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [NavigationDeps::class],
    modules = [NavigationModule::class]
)
internal interface NavigationComponent : NavigationApi {

    override fun provideRouter(): Router
    override fun provideNavigatorHolder(): NavigatorHolder
    override fun provideFragmentLauncher(): FragmentLauncher

    companion object {
        fun initAndGet(dependencies: NavigationDeps): NavigationComponent {
            return DaggerNavigationComponent.factory().create(dependencies)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(dependencies: NavigationDeps): NavigationComponent
    }
}