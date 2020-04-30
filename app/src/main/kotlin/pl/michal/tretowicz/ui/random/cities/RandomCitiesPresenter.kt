package pl.michal.tretowicz.ui.random.cities

import android.graphics.Color
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import org.joda.time.DateTime
import pl.michal.tretowicz.data.DataManager
import pl.michal.tretowicz.data.RxEventBus
import pl.michal.tretowicz.data.event.EventShowMap
import pl.michal.tretowicz.data.model.CityColorDate
import pl.michal.tretowicz.injection.ConfigPersistent
import pl.michal.tretowicz.ui.base.BasePresenter
import pl.michal.tretowicz.ui.base.MvpView
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


interface RandomCitiesMvpView : MvpView {
    fun addCity(cityColorDate: CityColorDate)
    fun showDetailsScreen(city: String, color: Int)
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

    private val random = Random()

    private fun emitValue() {
        val city = cities[random.nextInt(cities.size - 1)]
        val color = colorMap[colors[random.nextInt(colors.size - 1)]]!!
        val cityColorDate = CityColorDate(city, color, DateTime.now())
        view.addCity(cityColorDate)
    }

    fun onPause() {
        subscriptions.dispose()
        subscriptions = CompositeDisposable()
    }

    fun onResume() {
        Observable.interval(0, 5, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            emitValue()
                        },
                        onError = {

                        }
                ).addTo(subscriptions)
    }

    fun itemClicked(cityColorDate: CityColorDate, forTablet : Boolean) {
        if(forTablet) {
            rxEventBus.post(EventShowMap(cityColorDate.city))
        } else {
            view.showDetailsScreen(cityColorDate.city, cityColorDate.color)
        }
    }
}