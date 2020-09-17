package ro.crxapps.addresserly.locations.data.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import ro.crxapps.addresserly.core.data.uitls.UniqIdGenerator
import ro.crxapps.addresserly.core.network.NetworkStateMonitor
import ro.crxapps.addresserly.locations.data.api.LocationsApiResponse
import ro.crxapps.addresserly.locations.data.api.LocationsApiService
import ro.crxapps.addresserly.locations.data.models.AddressLocation
import javax.inject.Inject


class LocationsRepository @Inject constructor(
    private val application: Application,
    private val locationApiService: LocationsApiService,
    private val uniqIdGenerator: UniqIdGenerator,
) {
    private val cachedLocations: ArrayList<AddressLocation> = ArrayList()
    private val localLocations: ArrayList<AddressLocation> = ArrayList()
    private val locationsLiveData: MutableLiveData<ArrayList<AddressLocation>> = MutableLiveData()

    fun getLocationsLiveData(): MutableLiveData<ArrayList<AddressLocation>> {
        return locationsLiveData
    }

    fun getLocations(): ArrayList<AddressLocation> {
        return cachedLocations
    }

    suspend fun fetchLocationsList() {
        cachedLocations.clear()
        if (NetworkStateMonitor.isNetworkConnected) {
            val response: Response<LocationsApiResponse> = locationApiService.listLocation()
            if (response.isSuccessful && response.body()?.status == "ok") {
                response.body()?.locations?.let {
                    for (i in it.indices) {
                        if (it[i].id == null) {
                            it[i].id = uniqIdGenerator.provideUniqIdForAddressLocation(it[i])
                        }
                        insertLocationLocal(it[i])
                    }
                    cachedLocations.addAll(it)
                    locationsLiveData.postValue(cachedLocations)
                }
            }
        } else {
            loadLocalLocations()
        }
    }

    private fun insertLocationLocal(location: AddressLocation) {
        Realm.init(application)
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(location)
        realm.commitTransaction()
    }

    private fun loadLocalLocations() {
        Realm.init(application)
        Realm.getDefaultInstance().executeTransaction { realm ->
            localLocations.addAll(realm.where(AddressLocation::class.java).findAll())
            for(location in localLocations) {
                cachedLocations.add(AddressLocation.copy(location))
            }
            locationsLiveData.postValue(cachedLocations)
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