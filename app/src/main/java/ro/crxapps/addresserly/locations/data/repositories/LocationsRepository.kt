package ro.crxapps.addresserly.locations.data.repositories

import androidx.lifecycle.MutableLiveData
import retrofit2.Response
import ro.crxapps.addresserly.core.data.uitls.UniqIdGenerator
import ro.crxapps.addresserly.locations.data.api.LocationsApiResponse
import ro.crxapps.addresserly.locations.data.api.LocationsApiService
import ro.crxapps.addresserly.locations.data.models.AddressLocation
import javax.inject.Inject


class LocationsRepository @Inject constructor(
    private val locationApiService: LocationsApiService,
    private val uniqIdGenerator: UniqIdGenerator
) {
    private val cachedLocations = ArrayList<AddressLocation>()
    private val locationsLiveData = MutableLiveData<ArrayList<AddressLocation>>()

    fun getLocationsLiveData(): MutableLiveData<ArrayList<AddressLocation>> {
        return locationsLiveData
    }

    fun getLocations(): ArrayList<AddressLocation> {
        return cachedLocations
    }

    suspend fun fetchLocationsList() {
        if (cachedLocations.isEmpty()) {
            val response: Response<LocationsApiResponse> = locationApiService.listLocation()
            if (response.isSuccessful && response.body()?.status == "ok") {
                response.body()?.locations?.let {
                    for(i in it.indices) {
                        if (it[i].id == null) {
                            it[i].id = uniqIdGenerator.provideUniqIdForAddressLocation(it[i])
                        }
                    }
                    cachedLocations.addAll(it)

                    val testEmptyAddress: AddressLocation = AddressLocation()
                    testEmptyAddress.id = uniqIdGenerator.provideUniqIdForAddressLocation(testEmptyAddress)
                    cachedLocations.add(testEmptyAddress)

                    locationsLiveData.postValue(cachedLocations)
                }
            }
        }
    }

    suspend fun updateLocationDistance(values: HashMap<Long, Double>) {
        if (values.isNotEmpty()) {
            cachedLocations.forEach {
                it.id?.let { id ->
                    val distance: Double? = values[id]
                    if (distance != null) {
                        it.distance = distance
                    }
                }
            }

            locationsLiveData.postValue(cachedLocations)
        }
    }

    suspend fun getLocationById(id: Long): AddressLocation? {
        cachedLocations.forEach {
            if (it.id == id) {
                return it
            }
        }

        return null
    }
}