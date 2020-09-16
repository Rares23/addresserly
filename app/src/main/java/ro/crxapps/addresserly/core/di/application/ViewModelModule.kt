package ro.crxapps.addresserly.core.di.application

import androidx.lifecycle.ViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ro.crxapps.addresserly.locations.data.repositories.LocationsRepository
import ro.crxapps.addresserly.locations.viewmodels.LocationsListViewModel
import javax.inject.Provider
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

    @Provides
    @IntoMap
    @ViewModelKey(LocationsListViewModel::class)
    fun locationListViewModel(locationsRepository: LocationsRepository): ViewModel? {
        return LocationsListViewModel(locationsRepository)
    }
}