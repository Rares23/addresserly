package ro.crxapps.addresserly.locations.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ro.crxapps.addresserly.R
import ro.crxapps.addresserly.core.fragments.BaseFragment
import ro.crxapps.addresserly.locations.data.models.AddressLocation
import ro.crxapps.addresserly.locations.viewmodels.LocationDetailsViewModel
import javax.inject.Inject

class LocationDetailsFragment: BaseFragment() {
    @Inject
    lateinit var locationDetailsViewModel: LocationDetailsViewModel

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
        locationDetailsViewModel.locationLiveData.observe(viewLifecycleOwner, { location ->
            setLocationData(location)
        })
    }

    private fun setLocationData(location: AddressLocation) {
        label.text = location.label
        address.text = location.address
        coordinates.text = getString(R.string.coordinates, location.lat?.toString(), location.lng?.toString())
        distance.text = getString(R.string.distance, location.distance?.toInt().toString())
    }
}