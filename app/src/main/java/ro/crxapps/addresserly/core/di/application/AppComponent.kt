package ro.crxapps.addresserly.core.di.application

import dagger.Component
import ro.crxapps.addresserly.AddresserlyApp
import ro.crxapps.addresserly.core.network.NetworkStateMonitor
import ro.crxapps.addresserly.locations.viewmodels.LocationActionsViewModel
import ro.crxapps.addresserly.locations.viewmodels.LocationDetailsViewModel
import ro.crxapps.addresserly.locations.viewmodels.LocationsListViewModel
import javax.inject.Singleton

@Singleton
@Component(modules=[AppModule::class, ApiServicesModule::class, RepositoriesModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(application: AddresserlyApp)

    fun getLocationsViewModel(): LocationsListViewModel
    fun getLocationDetailsViewModel(): LocationDetailsViewModel
    fun getLocationActionsViewModel(): LocationActionsViewModel
    fun getNetworkStateMonitor(): NetworkStateMonitor
}