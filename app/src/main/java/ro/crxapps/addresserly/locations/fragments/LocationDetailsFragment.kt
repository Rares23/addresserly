package ro.crxapps.addresserly.locations.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import ro.crxapps.addresserly.R
import ro.crxapps.addresserly.core.fragments.BaseFragment
import ro.crxapps.addresserly.locations.data.models.AddressLocation
import ro.crxapps.addresserly.locations.viewmodels.LocationDetailsViewModel
import javax.inject.Inject

class LocationDetailsFragment : BaseFragment() {
    @Inject
    lateinit var locationDetailsViewModel: LocationDetailsViewModel

    val imageUrl: MutableLiveData<String?> = MutableLiveData()
    private lateinit var rootView: View
    private lateinit var label: TextView
    private lateinit var address: TextView
    private lateinit var coordinates: TextView
    private lateinit var distance: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_location_details, container, false)
        label = rootView.findViewById(R.id.label)
        address = rootView.findViewById(R.id.address)
        coordinates = rootView.findViewById(R.id.coordinates)
        distance = rootView.findViewById(R.id.distance)
        observeViewModelValues()
        val id: Long? = arguments?.getLong("id")
        id?.let {
            locationDetailsViewModel.loadLocation(it)
        }
        return rootView
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        getActivityComponent().inject(this)
    }

    private fun observeViewModelValues() {
        activity?.let {
            locationDetailsViewModel.locationLiveData.observe(it, { location ->
                setLocationData(location)
            })
        }
    }

    private fun setLocationData(location: AddressLocation) {
        label.text = location.label ?: getString(R.string.empty_label)
        address.text = location.address ?: getString(R.string.unknown_address)
        coordinates.text = if (location.lat == null || location.lng == null) {
            getString(R.string.no_coordinates)
        } else {
            getString(R.string.coordinates, location.lat?.toString(), location.lng?.toString())
        }
        val locationDistance: Double? = location.distance
        distance.text = if (locationDistance != null) {
            getString(R.string.distance, locationDistance.toInt().toString())
        } else {
            getString(R.string.invalid_distance)
        }

        imageUrl.postValue(location.image)
    }
}