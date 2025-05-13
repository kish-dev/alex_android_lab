package alex.android.lab.app

import alex.android.lab.app.di.ApplicationComponent
import alex.android.lab.app.di.DaggerApplicationComponent
import android.app.Application
import android.content.Context

class LabApplication : Application() {

    override fun onCreate() {
        appContext = applicationContext
        ApplicationComponent.init(DaggerApplicationComponent.factory().create(this))
        ApplicationComponent.get().inject(this)

        super.onCreate()
    }

    companion object {
        @Volatile
        lateinit var appContext: Context
            private set
    }
}