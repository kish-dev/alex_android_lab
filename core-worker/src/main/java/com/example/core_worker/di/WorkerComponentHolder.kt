package com.example.core_worker.di

import com.example.core_worker_api.WorkerApi
import com.example.module_injector.ComponentHolder

object WorkerComponentHolder : ComponentHolder<WorkerApi, WorkerDeps> {
    private var workerComponent: WorkerComponent? = null

    override fun init(dependencies: WorkerDeps) {
        if (workerComponent == null) {
            synchronized(WorkerComponentHolder::class.java) {
                if (workerComponent == null) {
                    workerComponent = WorkerComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): WorkerApi = getComponent()

    private fun getComponent(): WorkerComponent {
        checkNotNull(workerComponent) { "WorkerComponent was not initialized!" }
        return workerComponent!!
    }

    override fun reset() {
        workerComponent = null
    }
}