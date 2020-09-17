package ro.crxapps.addresserly.locations.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ro.crxapps.addresserly.locations.data.models.AddressLocation
import ro.crxapps.addresserly.locations.data.repositories.LocationsRepository
import javax.inject.Inject

class LocationDetailsViewModel @Inject constructor(private val locationsRepository: LocationsRepository) :
    ViewModel() {

    val locationLiveData: MutableLiveData<AddressLocation> = MutableLiveData()
    private var loadLocationJob: Job? = null
    fun loadLocation(locationId: Long) {
        loadLocationJob = CoroutineScope(Dispatchers.IO).launch {
            val location: AddressLocation? = locationsRepository.getLocationById(locationId)
            location?.let {
                locationLiveData.postValue(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        loadLocationJob?.cancel()
        loadLocationJob = null
    }
}