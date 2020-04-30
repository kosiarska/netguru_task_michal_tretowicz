package pl.michal.tretowicz.ui.main


import android.content.res.Configuration
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import pl.michal.tretowicz.R
import pl.michal.tretowicz.data.ToastManager
import pl.michal.tretowicz.ui.base.BaseActivity
import pl.michal.tretowicz.ui.random.cities.RandomCitiesFragment
import pl.michal.tretowicz.ui.random.cities.details.DetailsFragment
import pl.michal.tretowicz.ui.splash.SplashFragment
import pl.michal.tretowicz.util.extension.gone
import pl.michal.tretowicz.util.extension.visible
import javax.inject.Inject


/**
 * Created by Michał Trętowicz
 */
class MainActivity : BaseActivity(), MainMvpView {

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var toastManager: ToastManager

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tabletSize = resources.getBoolean(R.bool.isTablet)
        val orientation = this.resources.configuration.orientation
        if (tabletSize && orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_tablet)
        } else {
            setContentView(R.layout.activity_main)
        }


        activityComponent.inject(this)

        presenter.attachView(this)

        setSupportActionBar(toolbar)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showSplashScreen() {
        replaceFragment(R.id.frameSplash, SplashFragment())
    }

    override fun showMainScreen() {
        replaceFragment(R.id.frame, RandomCitiesFragment())
    }

    override fun showMapScreen(cityName: String) {
        replaceFragment(R.id.detailsFrame, DetailsFragment.newInstance(cityName))
    }

    override fun hideSplashScreen() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frameSplash)
        if(fragment != null) {
            supportFragmentManager.beginTransaction().hide(fragment).commit()
        }
    }

    override fun showToolbar() {
        toolbar.visible()
    }

    override fun hideToolbar() {
        toolbar.gone()
    }

    override fun setToolbarBackground(color: Int) {
        toolbar.setBackgroundColor(color)
    }

    override fun setToolbarTitle(cityName: String) {
        supportActionBar?.title = cityName
    }

    private fun replaceFragment(id : Int, fragment : Fragment) {
        supportFragmentManager.beginTransaction().replace(id, fragment).commit()
    }

    override fun setToolbarTitleGray() {
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.gray))
        toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.gray), PorterDuff.Mode.SRC_ATOP)
    }

    override fun setToolbarTitleWhite() {
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.press_twice_to_exit), Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
