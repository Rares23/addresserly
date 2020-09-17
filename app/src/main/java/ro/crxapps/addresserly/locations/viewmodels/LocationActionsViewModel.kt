package ro.crxapps.addresserly.locations.viewmodels

import androidx.lifecycle.ViewModel
import ro.crxapps.addresserly.locations.data.repositories.LocationsRepository
import javax.inject.Inject

class LocationActionsViewModel @Inject constructor(private val locationsRepository: LocationsRepository) : ViewModel() {
}