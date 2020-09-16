package ro.crxapps.addresserly.core.di.application

import dagger.Module
import dagger.Provides
import ro.crxapps.addresserly.core.data.uitls.UniqIdGenerator
import ro.crxapps.addresserly.locations.data.api.LocationsApiService
import ro.crxapps.addresserly.locations.data.repositories.LocationsRepository
import javax.inject.Singleton

@Module
class RepositoriesModule {
    @Singleton
    @Provides
    fun provideLocationsRepository(locationsApiService: LocationsApiService, uniqIdGenerator: UniqIdGenerator): LocationsRepository {
        return LocationsRepository(locationsApiService, uniqIdGenerator)
    }
}