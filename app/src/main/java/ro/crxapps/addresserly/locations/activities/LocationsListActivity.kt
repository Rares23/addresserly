package ro.crxapps.addresserly.locations.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import ro.crxapps.addresserly.R
import ro.crxapps.addresserly.core.activities.ActivityStarter
import ro.crxapps.addresserly.core.activities.BaseActivity
import ro.crxapps.addresserly.core.network.NetworkStateMonitor
import ro.crxapps.addresserly.locations.fragments.LocationDetailsFragment
import ro.crxapps.addresserly.locations.fragments.LocationsListFragment
import javax.inject.Inject


class LocationsListActivity : BaseActivity(),NetworkStateMonitor.NetworkConnectionListener  {

    @Inject
    lateinit var locationsListFragment: LocationsListFragment

    @Inject
    lateinit var activityStarter: ActivityStarter
    @Inject
    lateinit var networkStateMonitor: NetworkStateMonitor

    private lateinit var newLocationFab: FloatingActionButton
    private var connectionProblem: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations_list)
        getActivityComponent().inject(this)
        setLocationsListFragment(savedInstanceState)
        setViews()
        networkStateMonitor.addNetworkConnectionListener(this)
    }

    private fun setLocationsListFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val fragmentWrapper: FrameLayout = findViewById(R.id.fragment_wrapper)
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            fragmentTransaction
                .add(fragmentWrapper.id, locationsListFragment)
                .commit()
        } else {
            locationsListFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_wrapper) as LocationsListFragment
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationsListFragment.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setViews() {
        connectionProblem = findViewById(R.id.internet_connection_problem)
        newLocationFab = findViewById(R.id.new_location_fab)
        newLocationFab.setOnClickListener {
            activityStarter.openLocationActionsActivity()
        }
    }
    
    override fun onConnectionChangedState(isConnected: Boolean) {
        if(isConnected) {
            connectionProblem?.visibility = View.GONE
        } else {
            connectionProblem?.visibility = View.VISIBLE
        }
    }
}