package alex.android.lab

import alex.android.lab.di.components.DaggerApplicationComponent
import android.app.Application

class LabApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}