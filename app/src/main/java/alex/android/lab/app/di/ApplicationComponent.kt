package alex.android.lab.app.di

import alex.android.lab.app.LabApplication
import alex.android.lab.presentation.MainActivity
import android.app.Application
import com.example.core_utils.di.ApplicationScope
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        AppApiModule::class,
        AppDepsModule::class
    ]
)
internal interface ApplicationComponent {

    companion object {
        @Volatile
        private var instance: ApplicationComponent? = null

        fun get(): ApplicationComponent {
            return checkNotNull(
                instance,
                { "ApplicationComponent is not initialized yet. Call init first." }
            )
        }

        fun init(component: ApplicationComponent) {
            require(instance == null) { "ApplicationComponent is already initialized." }
            instance = component
        }
    }

    fun inject(application: LabApplication)
    fun inject(activity: MainActivity)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}