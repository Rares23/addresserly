package ro.crxapps.addresserly.locations.viewmodels

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ro.crxapps.addresserly.locations.data.models.AddressLocation
import ro.crxapps.addresserly.locations.data.repositories.LocationsRepository
import ro.crxapps.addresserly.locations.utils.DistanceCalculator
import javax.inject.Inject

class LocationsListViewModel @Inject constructor(
    private val locationsRepository: LocationsRepository,
    private val distanceCalculator: DistanceCalculator
) : ViewModel() {
    val loadingLocations: MutableLiveData<Boolean> = MutableLiveData()
    private var currentLocation: Location? = null
    private var loadLocationsJob: Job? = null
    private var currentLocationUpdateJob: Job? = null
    private var firstLoad: Boolean = false

    fun loadLocations(forceLoad: Boolean = false) {
        if(forceLoad || !firstLoad) {
            loadingLocations.postValue(true)
            loadLocationsJob = CoroutineScope(Dispatchers.IO).launch {
                firstLoad = true
                locationsRepository.fetchLocationsList()
                updateAddressLocationsDistances()
                loadingLocations.postValue(false)
            }
        }
    }

    fun getLocationsLiveData(): MutableLiveData<ArrayList<AddressLocation>> {
        return locationsRepository.getLocationsLiveData()
    }

    fun setCurrentLocation(location: Location?) {
        currentLocation = location
        updateAddressLocationsDistances()
    }

    private fun updateAddressLocationsDistances() {
        currentLocation?.let {
            currentLocationUpdateJob = CoroutineScope(Dispatchers.IO).launch {
                val distanceValues: HashMap<Long, Double> = HashMap()
                locationsRepository.getLocations().forEach {addressLocation->
                    val locationLat: Double = (addressLocation.lat ?: .0).toDouble()
                    val locationLng: Double = (addressLocation.lng ?: .0).toDouble()
                    val currentLocationLat: Double = it.latitude
                    val currentLocationLng: Double = it.longitude

                    if(locationLat != .0 && locationLng != .0) {
                        val distance: Double = distanceCalculator.calculateDistance(
                            locationLat,
                            locationLng,
                            currentLocationLat,
                            currentLocationLng
                        )

                        addressLocation.id?.let { id ->
                            distanceValues[id] = distance
                        }
                    }
                }

                locationsRepository.updateLocationDistance(distanceValues)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        loadLocationsJob?.cancel()
        loadLocationsJob = null

        currentLocationUpdateJob?.cancel()
        currentLocationUpdateJob = null
    }

}