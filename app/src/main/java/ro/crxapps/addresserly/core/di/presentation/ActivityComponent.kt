package ro.crxapps.addresserly.core.di.presentation

import dagger.Component
import ro.crxapps.addresserly.core.di.application.AppComponent
import ro.crxapps.addresserly.locations.activities.LocationsListActivity
import ro.crxapps.addresserly.locations.fragments.LocationsListFragment

@ActivityScope
@Component(dependencies = [AppComponent::class], modules= [ActivityModule::class])
interface ActivityComponent {
    fun inject(locationsListActivity: LocationsListActivity)
    fun inject(locationsListFragment: LocationsListFragment)
}