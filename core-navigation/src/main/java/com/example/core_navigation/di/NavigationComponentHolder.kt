package com.example.core_navigation.di

import com.example.core_navigation_api.NavigationApi
import com.example.module_injector.ComponentHolder

object NavigationComponentHolder : ComponentHolder<NavigationApi, NavigationDeps> {
    private var dbComponent: NavigationComponent? = null

    override fun init(dependencies: NavigationDeps) {
        if (dbComponent == null) {
            synchronized(NavigationComponentHolder::class.java) {
                if (dbComponent == null) {
                    dbComponent = NavigationComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): NavigationApi = getComponent()

    private fun getComponent(): NavigationComponent {
        checkNotNull(dbComponent) { "NavigationComponent was not initialized!" }
        return dbComponent!!
    }

    override fun reset() {
        dbComponent = null
    }
}