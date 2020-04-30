package pl.michal.tretowicz.ui.splash

import kotlinx.android.synthetic.main.fragment_splash.*
import pl.michal.tretowicz.R
import pl.michal.tretowicz.ui.base.BaseFragment
import pl.michal.tretowicz.util.extension.load


class SplashFragment : BaseFragment() {
    override fun getLayoutResId() = R.layout.fragment_splash


    override fun attachView() {
        super.attachView()
        netguruLogo.load(R.drawable.netguru_logo) { it.fit().centerInside() }
    }
}