package pl.michal.tretowicz.ui.random.cities

import kotlinx.android.synthetic.main.fragment_random_cities.*
import pl.michal.tretowicz.R
import pl.michal.tretowicz.data.model.CityColorDate
import pl.michal.tretowicz.ui.base.BaseFragment
import pl.michal.tretowicz.ui.random.cities.adapter.RandomCitiesAdapter
import javax.inject.Inject


class RandomCitiesFragment : BaseFragment(), RandomCitiesMvpView {

    @Inject
    lateinit var presenter: RandomCitiesPresenter

    @Inject
    lateinit var adapter: RandomCitiesAdapter

    override fun getLayoutResId() = R.layout.fragment_random_cities

    override fun attachView() {
        super.attachView()
        activityComponent().inject(this)
        presenter.attachView(this)

        list.adapter = adapter
    }

    override fun detachView() {
        super.detachView()
        presenter.detachView()
    }

    override fun addCity(cityColorDate: CityColorDate) {
        adapter.addCity(cityColorDate)
    }
}