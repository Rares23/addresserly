package ro.crxapps.addresserly.locations.data.repositories

import ro.crxapps.addresserly.locations.data.api.LocationsApiService
import javax.inject.Inject


class LocationsRepository {
    @Inject
    lateinit var locationApiService: LocationsApiService

    fun fetchLocationsList() {
        //fetch locations
    }
}