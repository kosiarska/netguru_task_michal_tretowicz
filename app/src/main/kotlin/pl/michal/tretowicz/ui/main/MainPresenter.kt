package pl.michal.tretowicz.ui.main


import android.graphics.Color
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import pl.michal.tretowicz.data.DataManager
import pl.michal.tretowicz.data.RxEventBus
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

}

/**
 * Created by Michał Trętowicz
 */
@ConfigPersistent
class MainPresenter
@Inject
constructor(private val rxEventBus: RxEventBus, private val sessionManager: SessionManager, private val dataManager: DataManager) : BasePresenter<MainMvpView>() {



    override fun attachView(view: MainMvpView) {
        super.attachView(view)

        view.showSplashScreen()
        view.hideToolbar()

        Observable.just(true).delaySubscription(2, TimeUnit.SECONDS)
                .subscribeBy(
                        onNext = {
                            view.showMainScreen()
                            view.showToolbar()
                        },
                        onError = {

                        }
                ).addTo(subscriptions)
    }

}
