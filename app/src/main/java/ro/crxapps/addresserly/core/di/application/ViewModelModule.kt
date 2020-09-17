package ro.crxapps.addresserly.core.di.application

import androidx.lifecycle.ViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ro.crxapps.addresserly.locations.data.repositories.LocationsRepository
import ro.crxapps.addresserly.locations.utils.DistanceCalculator
import ro.crxapps.addresserly.locations.viewmodels.LocationDetailsViewModel
import ro.crxapps.addresserly.locations.viewmodels.LocationsListViewModel
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass


@Module
class ViewModelModule() {
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Provides
    fun viewModelFactory(providerMap: Map<Class<out ViewModel>, Provider<ViewModel>>): ViewModelFactory? {
        return ViewModelFactory(providerMap)
    }

    @Singleton
    @Provides
    @IntoMap
    @ViewModelKey(LocationsListViewModel::class)
    fun locationsListViewModel(locationsRepository: LocationsRepository, distanceCalculator: DistanceCalculator): LocationsListViewModel {
        return LocationsListViewModel(locationsRepository, distanceCalculator)
    }

    @Singleton
    @Provides
    @IntoMap
    @ViewModelKey(LocationDetailsViewModel::class)
    fun locationDetailsViewModel(locationsRepository: LocationsRepository): LocationDetailsViewModel {
        return LocationDetailsViewModel(locationsRepository)
    }
}