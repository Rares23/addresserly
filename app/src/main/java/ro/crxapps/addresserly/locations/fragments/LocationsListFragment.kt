package ro.crxapps.addresserly.locations.fragments

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ro.crxapps.addresserly.R
import ro.crxapps.addresserly.core.fragments.BaseFragment
import ro.crxapps.addresserly.core.fragments.FragmentTags
import ro.crxapps.addresserly.locations.adapters.LocationsListAdapter
import ro.crxapps.addresserly.locations.utils.GPSLocationProvider
import ro.crxapps.addresserly.locations.viewmodels.LocationsListViewModel
import javax.inject.Inject


class LocationsListFragment : BaseFragment(), GPSLocationProvider.OnCurrentLocationLoadListener {
    companion object {
        const val TAG: String = FragmentTags.LOCATIONS_LIST
    }

    @Inject
    lateinit var locationsListViewModel: LocationsListViewModel
    @Inject
    lateinit var locationsListAdapter: LocationsListAdapter
    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager
    @Inject
    lateinit var gpsLocationProvider: GPSLocationProvider

    private lateinit var rootView: View
    private lateinit var locationsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_locations_list, container, false)
        initRecyclerView()
        observeViewModelValues()

        locationsListViewModel.loadLocations()
        getCurrentLocation()
        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getActivityComponent().inject(this)
    }

    private fun initRecyclerView() {
        locationsRecyclerView = rootView.findViewById(R.id.locations_list)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        locationsRecyclerView.layoutManager = linearLayoutManager
        locationsRecyclerView.adapter = locationsListAdapter
    }

    private fun observeViewModelValues() {
        locationsListViewModel.getLocationsLiveData().observe(viewLifecycleOwner, { locations ->
            locationsListAdapter.setLocations(locations)
        })
    }

    override fun getCurrentLocation(currentLocation: Location) {
        locationsListViewModel.setCurrentLocation(currentLocation)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        getCurrentLocation()
    }

    private fun getCurrentLocation() {
        gpsLocationProvider.loadCurrentGPSLocation(this)
    }
}