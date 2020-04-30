package pl.michal.tretowicz.ui.random.cities

import android.content.res.Configuration
import android.view.View
import kotlinx.android.synthetic.main.fragment_random_cities.*
import pl.michal.tretowicz.R
import pl.michal.tretowicz.data.model.CityColorDate
import pl.michal.tretowicz.ui.base.BaseFragment
import pl.michal.tretowicz.ui.random.cities.adapter.RandomCitiesAdapter
import pl.michal.tretowicz.ui.random.cities.details.DetailsActivity
import pl.michal.tretowicz.util.extension.startActivity
import java.util.ArrayList
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
        adapter.onClickListener = View.OnClickListener {

            val tabletSize = resources.getBoolean(R.bool.isTablet)
            val orientation = this.resources.configuration.orientation
            presenter.itemClicked(it.tag as CityColorDate,tabletSize && orientation == Configuration.ORIENTATION_LANDSCAPE)
        }
    }

    override fun detachView() {
        super.detachView()
        presenter.detachView()
    }

    override fun showData(list: ArrayList<CityColorDate>) {
        adapter.showData(list)
    }

    override fun showDetailsScreen(city: String, color: Int) {
        startActivity<DetailsActivity>(DetailsActivity.EXTRA_TEXT to city, DetailsActivity.EXTRA_COLOR to color)
    }
}