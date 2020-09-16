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
                    for (location in it) {
                        if (location.id == null) {
                            val firstValue: Double = (location.lat ?: -1).toDouble()
                            val secondValue: Double = (location.lng ?: -1).toDouble()
                            if (firstValue != (-1).toDouble() && secondValue != (-1).toDouble()) {
                                location.id = uniqIdGenerator.provideUniqIdFromTwoValues(
                                    firstValue,
                                    secondValue
                                )
                            }
                        }
                    }
                    cachedLocations.addAll(it)
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
}