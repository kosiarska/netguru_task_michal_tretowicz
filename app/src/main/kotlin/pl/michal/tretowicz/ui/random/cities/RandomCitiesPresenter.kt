package pl.michal.tretowicz.ui.random.cities

import android.graphics.Color
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import org.joda.time.DateTime
import pl.michal.tretowicz.data.DataManager
import pl.michal.tretowicz.data.RxEventBus
import pl.michal.tretowicz.data.event.EventAppInBackground
import pl.michal.tretowicz.data.event.EventAppInForeground
import pl.michal.tretowicz.data.event.EventShowMap
import pl.michal.tretowicz.data.model.CityColorDate
import pl.michal.tretowicz.injection.ConfigPersistent
import pl.michal.tretowicz.ui.base.BasePresenter
import pl.michal.tretowicz.ui.base.MvpView
import java.text.Collator
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


interface RandomCitiesMvpView : MvpView {
    fun showDetailsScreen(city: String, color: Int)
    fun showData(list: ArrayList<CityColorDate>)
}

@ConfigPersistent
class RandomCitiesPresenter @Inject constructor(private val dataManager: DataManager, private val rxEventBus: RxEventBus) : BasePresenter<RandomCitiesMvpView>() {

    private val cities = listOf("Gdańsk", "Warszawa", "Poznań", "Białystok", "Wrocław", "Katowice", "Kraków")
    private val colors = listOf("Yellow", "Green", "Blue", "Red", "Black", "White")

    private val colorMap = hashMapOf(
            colors[0] to Color.YELLOW,
            colors[1] to Color.GREEN,
            colors[2] to Color.BLUE,
            colors[3] to Color.RED,
            colors[4] to Color.BLACK,
            colors[5] to Color.WHITE
    )

    private val list = arrayListOf<CityColorDate>()
    private val random = Random()
    private lateinit var subscription: Disposable
    private var onlyOnce = false

    private fun emitValue() {
        val city = cities[random.nextInt(cities.size)]
        val color = colorMap[colors[random.nextInt(colors.size)]]!!
        val cityColorDate = CityColorDate(city, color, DateTime.now())

        list.add(cityColorDate)
        list.sortWith(Comparator { a, b -> Collator.getInstance().compare(a.city, b.city) })

        view.showData(list)
    }

    private fun scheduleUpdates() {
        subscription = Observable.interval(0, 5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            emitValue()
                        },
                        onError = {
                        }
                )
        onlyOnce = true
    }

    override fun attachView(view: RandomCitiesMvpView) {
        super.attachView(view)
        if (!onlyOnce) {
            scheduleUpdates()
        }

        rxEventBus.filteredObservable(EventAppInForeground::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            if (subscription.isDisposed) {
                                scheduleUpdates()
                            }
                        },
                        onError = {

                        }
                ).addTo(subscriptions)

        rxEventBus.filteredObservable(EventAppInBackground::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            subscription.dispose()
                            onlyOnce = false
                        },
                        onError = {

                        }
                ).addTo(subscriptions)
    }

    fun itemClicked(cityColorDate: CityColorDate, forTablet: Boolean) {
        if (forTablet) {
            rxEventBus.post(EventShowMap(cityColorDate.city, cityColorDate.color))
        } else {
            view.showDetailsScreen(cityColorDate.city, cityColorDate.color)
        }
    }
}