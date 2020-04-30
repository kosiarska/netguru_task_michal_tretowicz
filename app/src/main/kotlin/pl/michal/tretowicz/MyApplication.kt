package pl.michal.tretowicz


import pl.michal.tretowicz.injection.component.ApplicationComponent
import pl.michal.tretowicz.injection.component.DaggerApplicationComponent
import pl.michal.tretowicz.injection.module.ApplicationModule
import pl.michal.tretowicz.util.Preferences
import timber.log.Timber


open class MyApplication : android.app.Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        initDaggerComponent()
        Preferences.init(this)

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }


    @androidx.annotation.VisibleForTesting
    private fun initDaggerComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
