package com.example.core_db.di

import com.example.core_db_api.DbApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [DbDeps::class],
    modules = [DbModule::class]
)
internal interface DbComponent {

    companion object {
        fun initAndGet(dependencies: DbDeps): DbComponent {
            return DaggerDbComponent.factory().create(dependencies)
        }
    }

    //тут не должно быть provides, оно уже есть в AppApiModule
    fun provideDbApi(): DbApi

    @Component.Factory
    interface Factory {
        fun create(dependencies: DbDeps): DbComponent
    }
}