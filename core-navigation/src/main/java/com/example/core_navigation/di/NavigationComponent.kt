package com.example.core_navigation.di

import com.example.core_navigation_api.NavigationApi
import com.example.core_utils.di.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(
    dependencies = [NavigationDeps::class],
    modules = [NavigationModule::class]
)
internal interface NavigationComponent : NavigationApi {

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