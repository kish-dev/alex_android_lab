package com.example.core_db.di

import com.example.core_db_api.DbProvideApi
import com.example.module_injector.ComponentHolder

object DbComponentHolder : ComponentHolder<DbProvideApi, DbDeps> {
    private var dbComponent: DbComponent? = null

    override fun init(dependencies: DbDeps) {
        if (dbComponent == null) {
            synchronized(DbComponentHolder::class.java) {
                if (dbComponent == null) {
                    dbComponent = DbComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): DbProvideApi = getComponent()

    private fun getComponent(): DbComponent {
        checkNotNull(dbComponent) { "DbComponent was not initialized!" }
        return dbComponent!!
    }

    override fun reset() {
        dbComponent = null
    }
}