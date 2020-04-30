package pl.michal.tretowicz.ui.main


import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import pl.michal.tretowicz.R
import pl.michal.tretowicz.data.ToastManager
import javax.inject.Inject
import pl.michal.tretowicz.ui.base.BaseActivity
import pl.michal.tretowicz.ui.splash.SplashFragment
import pl.michal.tretowicz.util.extension.gone
import pl.michal.tretowicz.util.extension.visible

/**
 * Created by Michał Trętowicz
 */
class MainActivity : BaseActivity(), MainMvpView {

    @Inject
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var toastManager: ToastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent.inject(this)

        presenter.attachView(this)

        setSupportActionBar(toolbar)

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showSplashScreen() {
        supportFragmentManager.beginTransaction().replace(R.id.frame, SplashFragment()).commit()
    }

    override fun showToolbar() {
        toolbar.visible()
    }

    override fun hideToolbar() {
        toolbar.gone()
    }

    override fun showMainScreen() {
       
    }
}
