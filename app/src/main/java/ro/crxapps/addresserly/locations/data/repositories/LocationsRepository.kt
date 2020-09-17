package ro.crxapps.addresserly.locations.data.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ro.crxapps.addresserly.core.data.uitls.UniqIdGenerator
import ro.crxapps.addresserly.core.network.NetworkStateMonitor
import ro.crxapps.addresserly.locations.data.api.LocationsApiResponse
import ro.crxapps.addresserly.locations.data.api.LocationsApiService
import ro.crxapps.addresserly.locations.data.models.AddressLocation
import kotlin.Exception
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
        if (NetworkStateMonitor.isNetworkConnected) {
            try {
                val response: LocationsApiResponse = locationApiService.listLocation()
                if(response.status == "ok") {
                    for (i in response.locations.indices) {
                        if (response.locations[i].id == null) {
                            response.locations[i].id = uniqIdGenerator.provideUniqIdForAddressLocation(response.locations[i])
                        }
                        insertLocationLocal(response.locations[i])
                    }
                    cachedLocations.clear()
                    cachedLocations.addAll(response.locations)
                    locationsLiveData.postValue(cachedLocations)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                locationsLiveData.postValue(ArrayList())
            }
        } else {
            withContext(Dispatchers.Main) {
                loadLocalLocations()
            }
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
            localLocations.clear()
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

    fun getLocationById(id: Long): AddressLocation? {
        cachedLocations.forEach {
            if (it.id == id) {
                return it
            }
        }

        return null
    }
}