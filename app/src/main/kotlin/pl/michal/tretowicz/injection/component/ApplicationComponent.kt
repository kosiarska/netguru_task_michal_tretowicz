package pl.michal.tretowicz.injection.component

import android.app.Application
import android.content.Context
import dagger.Component
import pl.michal.tretowicz.MyApplication
import pl.michal.tretowicz.data.DataManager
import pl.michal.tretowicz.data.RxEventBus
import pl.michal.tretowicz.data.remote.ApiService
import pl.michal.tretowicz.data.repository.session.SessionManager
import pl.michal.tretowicz.data.repository.session.SessionRepository
import pl.michal.tretowicz.injection.ApplicationContext
import pl.michal.tretowicz.injection.module.ApplicationModule
import pl.michal.tretowicz.injection.module.DataModule
import pl.michal.tretowicz.util.validation.LoginValidator
import pl.michal.tretowicz.util.validation.PasswordValidator
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, DataModule::class))
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application
    fun apiService(): ApiService
    fun loginManager(): SessionManager
    fun sessionRepository(): SessionRepository
    fun dataManager() : DataManager

    fun loginValidator(): LoginValidator
    fun passwordValidator(): PasswordValidator

    fun eventBus(): RxEventBus

    fun inject(myApplication: MyApplication)

}
