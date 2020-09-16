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
    var locationsRepository: LocationsRepository,
    var distanceCalculator: DistanceCalculator
) : ViewModel() {

    private var currentLocation: Location? = null
    private var loadLocationsJob: Job? = null
    private var currentLocationUpdateJob: Job? = null
    fun loadLocations() {
        loadLocationsJob = CoroutineScope(Dispatchers.Main).launch {
            locationsRepository.fetchLocationsList()
            updateAddressLocationsDistances()
        }
    }

    fun getLocationsLiveData(): MutableLiveData<ArrayList<AddressLocation>> {
        return locationsRepository.getLocationsLiveData()
    }

    fun setCurrentLocation(location: Location) {
        currentLocation = location
        updateAddressLocationsDistances()
    }

    private fun updateAddressLocationsDistances() {
        currentLocation?.let {
            currentLocationUpdateJob = CoroutineScope(Dispatchers.IO).launch {
                val distanceValues: HashMap<Long, Double> = HashMap()
                locationsRepository.getLocations().forEach {addressLocation->
                    val locationLat: Double = (addressLocation.lat ?: -1).toDouble()
                    val locationLng: Double = (addressLocation.lng ?: -1).toDouble()
                    val currentLocationLat: Double = it.latitude
                    val currentLocationLng: Double = it.longitude
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