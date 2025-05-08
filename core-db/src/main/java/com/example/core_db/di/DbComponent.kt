package com.example.core_db.di

import com.example.core_db_api.DbApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [DbDeps::class],
    modules = [DbModule::class]
)
internal interface DbComponent : DbApi {

    companion object {
        fun initAndGet(dependencies: DbDeps): DbComponent {
            return DaggerDbComponent.factory().create(dependencies)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(dependencies: DbDeps): DbComponent
    }
}