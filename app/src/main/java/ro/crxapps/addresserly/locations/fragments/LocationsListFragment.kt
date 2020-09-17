package ro.crxapps.addresserly.locations.fragments

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import ro.crxapps.addresserly.R
import ro.crxapps.addresserly.core.fragments.BaseFragment
import ro.crxapps.addresserly.core.network.NetworkStateMonitor
import ro.crxapps.addresserly.locations.activities.LocationDetailsActivity
import ro.crxapps.addresserly.locations.adapters.LocationsListAdapter
import ro.crxapps.addresserly.locations.data.models.AddressLocation
import ro.crxapps.addresserly.locations.utils.GPSLocationProvider
import ro.crxapps.addresserly.locations.viewmodels.LocationDetailsViewModel
import ro.crxapps.addresserly.locations.viewmodels.LocationsListViewModel
import javax.inject.Inject


class LocationsListFragment : BaseFragment(), GPSLocationProvider.OnCurrentLocationLoadListener, NetworkStateMonitor.NetworkConnectionListener {

    @Inject
    lateinit var locationsListViewModel: LocationsListViewModel
    @Inject
    lateinit var locationsListAdapter: LocationsListAdapter
    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager
    @Inject
    lateinit var gpsLocationProvider: GPSLocationProvider
    @Inject
    lateinit var snapHelper: LinearSnapHelper
    @Inject
    lateinit var networkStateMonitor: NetworkStateMonitor

    private lateinit var rootView: View
    private lateinit var locationsRecyclerView: RecyclerView
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var emptyContentView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_locations_list, container, false)
        initViews()
        networkStateMonitor.addNetworkConnectionListener(this)

        observeViewModelValues()
        locationsListViewModel.loadLocations()
        getCurrentLocation()
        return rootView
    }

    private fun initViews() {
        loadingProgressBar = rootView.findViewById(R.id.loading)
        emptyContentView = rootView.findViewById(R.id.empty_content)
        initRecyclerView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getActivityComponent().inject(this)
    }

    override fun onDetach() {
        super.onDetach()
        networkStateMonitor.removeNetworkConnectionListener(this)
    }

    private fun initRecyclerView() {
        locationsRecyclerView = rootView.findViewById(R.id.locations_list)
        locationsListAdapter.setOnLocationClickListener(object: LocationsListAdapter.OnLocationClickListener {
            override fun onLocationClick(location: AddressLocation) {
                location.id?.let {
                    showLocationDetails(it)
                }
            }
        })
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        locationsRecyclerView.layoutManager = linearLayoutManager
        locationsRecyclerView.adapter = locationsListAdapter
        snapHelper.attachToRecyclerView(locationsRecyclerView)
    }

    private fun showLocationDetails(locationId: Long) {
        val intent: Intent = Intent(context, LocationDetailsActivity::class.java)
        val bundle: Bundle = Bundle()
        bundle.putLong("id", locationId)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun observeViewModelValues() {
        locationsListViewModel.getLocationsLiveData().observe(viewLifecycleOwner, { locations ->
            if(locations.isEmpty()) {
                emptyContentView.visibility = View.VISIBLE
                locationsRecyclerView.visibility = View.INVISIBLE
            } else {
                locationsListAdapter.setLocations(locations)
                emptyContentView.visibility = View.GONE
                locationsRecyclerView.visibility = View.VISIBLE
            }

        })

        locationsListViewModel.loadingLocations.observe(viewLifecycleOwner, { loading ->
            if(loading) {
                locationsRecyclerView.visibility = View.INVISIBLE
                loadingProgressBar.visibility = View.VISIBLE
                emptyContentView.visibility = View.GONE
            } else {
                locationsRecyclerView.visibility = View.VISIBLE
                loadingProgressBar.visibility = View.GONE
            }
        })
    }

    override fun getCurrentLocation(currentLocation: Location?) {
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

    override fun onConnectionChangedState(isConnected: Boolean) {
        if(isConnected) {
            locationsListViewModel.loadLocations()
        } else {
            //show internet connection problem
        }
    }
}