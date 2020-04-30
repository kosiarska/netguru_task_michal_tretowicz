package pl.michal.tretowicz


import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.github.ajalt.timberkt.e
import pl.michal.tretowicz.data.RxEventBus
import pl.michal.tretowicz.data.event.EventAppInBackground
import pl.michal.tretowicz.data.event.EventAppInForeground
import pl.michal.tretowicz.injection.component.ApplicationComponent
import pl.michal.tretowicz.injection.component.DaggerApplicationComponent
import pl.michal.tretowicz.injection.module.ApplicationModule
import pl.michal.tretowicz.util.Preferences
import timber.log.Timber
import javax.inject.Inject


open class MyApplication : android.app.Application(), LifecycleObserver {

    lateinit var applicationComponent: ApplicationComponent
        private set

    @Inject
    lateinit var rxEventBus: RxEventBus

    override fun onCreate() {
        super.onCreate()
        initDaggerComponent()
        applicationComponent.inject(this)
        Preferences.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        e { "App in background" }
        rxEventBus.post(EventAppInBackground())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        e { "App in foreground" }
        rxEventBus.post(EventAppInForeground())
    }

    @androidx.annotation.VisibleForTesting
    private fun initDaggerComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
