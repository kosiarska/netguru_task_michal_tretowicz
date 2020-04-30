package pl.michal.tretowicz.injection.component

import dagger.Subcomponent
import pl.michal.tretowicz.injection.PerActivity
import pl.michal.tretowicz.injection.module.ActivityModule
import pl.michal.tretowicz.ui.main.MainActivity
import pl.michal.tretowicz.ui.random.cities.RandomCitiesFragment

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(fragment: RandomCitiesFragment)
}
