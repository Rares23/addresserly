package ro.crxapps.addresserly.core.di.application

import android.app.Application
import dagger.Module
import dagger.Provides
import io.realm.Realm
import ro.crxapps.addresserly.core.data.uitls.UniqIdGenerator
import ro.crxapps.addresserly.locations.data.api.LocationsApiService
import ro.crxapps.addresserly.locations.data.repositories.LocationsRepository
import javax.inject.Singleton

@Module
class RepositoriesModule {
    @Singleton
    @Provides
    fun provideLocationsRepository(
            application: Application,
            locationsApiService: LocationsApiService,
            uniqIdGenerator: UniqIdGenerator): LocationsRepository {
        return LocationsRepository(application, locationsApiService, uniqIdGenerator)
    }
}