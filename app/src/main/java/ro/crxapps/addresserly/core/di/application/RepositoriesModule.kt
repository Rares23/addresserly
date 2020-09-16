package ro.crxapps.addresserly.core.di.application

import dagger.Module
import dagger.Provides
import ro.crxapps.addresserly.locations.data.repositories.LocationsRepository
import javax.inject.Singleton

@Module
class RepositoriesModule {
    @Singleton
    @Provides
    fun provideLocationsRepository(): LocationsRepository {
        return LocationsRepository()
    }
}