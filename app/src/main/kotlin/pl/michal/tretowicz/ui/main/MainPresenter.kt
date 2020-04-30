package pl.michal.tretowicz.ui.main


import android.graphics.Color
import android.graphics.PorterDuff
import androidx.core.content.ContextCompat
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_details.*
import pl.michal.tretowicz.R
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
    fun hideSplashScreen()
    fun setToolbarTitleGray()
    fun setToolbarTitleWhite()

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
                                view.hideSplashScreen()
                                view.showMainScreen()
                                view.showToolbar()
                            },
                            onError = {

                            }
                    ).addTo(subscriptions)
        } else {
            view.hideSplashScreen()
            view.showMainScreen()
        }

        rxEventBus.filteredObservable(EventShowMap::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            view.showMapScreen(it.cityName)
                            view.setToolbarTitle(it.cityName)
                            view.setToolbarBackground(it.color)

                            if(it.color == Color.WHITE || it.color == Color.YELLOW) {
                                view.setToolbarTitleGray()
                            } else {
                                view.setToolbarTitleWhite()
                            }
                        },
                        onError = {

                        }
                ).addTo(subscriptions)
    }

}
