package pl.michal.tretowicz.ui.main


import android.graphics.Color
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import pl.michal.tretowicz.data.DataManager
import pl.michal.tretowicz.data.RxEventBus
import pl.michal.tretowicz.data.event.EventShowMap
import pl.michal.tretowicz.data.repository.session.SessionManager
import pl.michal.tretowicz.injection.ConfigPersistent
import pl.michal.tretowicz.ui.base.BasePresenter
import pl.michal.tretowicz.ui.base.MvpView
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface MainMvpView : MvpView {
    fun showSplashScreen()
    fun hideToolbar()
    fun showMainScreen()
    fun showToolbar()
    fun showMapScreen(cityName: String)
    fun setToolbarTitle(cityName: String)
    fun setToolbarBackground(color: Int)

}

/**
 * Created by Michał Trętowicz
 */
@ConfigPersistent
class MainPresenter
@Inject
constructor(private val rxEventBus: RxEventBus, private val sessionManager: SessionManager, private val dataManager: DataManager) : BasePresenter<MainMvpView>() {

    var shownSplashOnce = false

    override fun attachView(view: MainMvpView) {
        super.attachView(view)

        if(!shownSplashOnce) {
            view.hideToolbar()
            view.showSplashScreen()
            Observable.just(true).delaySubscription(2, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onNext = {
                                shownSplashOnce = true
                                view.showMainScreen()
                                view.showToolbar()
                            },
                            onError = {

                            }
                    ).addTo(subscriptions)
        } else {
            view.showMainScreen()
        }

        rxEventBus.filteredObservable(EventShowMap::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            view.showMapScreen(it.cityName)
                            view.setToolbarTitle(it.cityName)
                            view.setToolbarBackground(it.color)
                        },
                        onError = {

                        }
                ).addTo(subscriptions)
    }

}
