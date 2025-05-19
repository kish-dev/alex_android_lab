package com.example.core_worker.di

import com.example.core_utils.di.ApplicationScope
import com.example.core_worker_api.WorkerApi
import dagger.Component

@ApplicationScope
@Component(
    dependencies = [WorkerDeps::class],
    modules = [WorkerModule::class]
)
internal interface WorkerComponent : WorkerApi {

    companion object {
        fun initAndGet(dependencies: WorkerDeps): WorkerComponent {
            return DaggerWorkerComponent.factory().create(dependencies)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(dependencies: WorkerDeps): WorkerComponent
    }
}