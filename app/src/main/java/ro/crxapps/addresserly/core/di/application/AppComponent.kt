package ro.crxapps.addresserly.core.di.application

import dagger.Component
import ro.crxapps.addresserly.locations.activities.LocationsListActivity
import ro.crxapps.addresserly.locations.viewmodels.LocationDetailsViewModel
import ro.crxapps.addresserly.locations.viewmodels.LocationsListViewModel
import javax.inject.Singleton

@Singleton
@Component(modules=[AppModule::class, ApiServicesModule::class, RepositoriesModule::class, ViewModelModule::class])
interface AppComponent {
    fun getLocationsViewModel(): LocationsListViewModel
    fun getLocationDetailsViewModel(): LocationDetailsViewModel
}