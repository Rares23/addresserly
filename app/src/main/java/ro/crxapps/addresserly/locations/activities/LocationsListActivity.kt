package ro.crxapps.addresserly.locations.activities

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ro.crxapps.addresserly.R
import ro.crxapps.addresserly.core.activities.ActivityStarter
import ro.crxapps.addresserly.core.activities.BaseActivity
import ro.crxapps.addresserly.locations.fragments.LocationsListFragment
import javax.inject.Inject


class LocationsListActivity : BaseActivity() {

    @Inject
    lateinit var locationsListFragment: LocationsListFragment

    @Inject
    lateinit var activityStarter: ActivityStarter

    private lateinit var newLocationFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations_list)
        getActivityComponent().inject(this)
        setLocationsListFragment(savedInstanceState)
        setViews()
    }

    private fun setLocationsListFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val fragmentWrapper: FrameLayout = findViewById(R.id.fragment_wrapper)
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            fragmentTransaction
                .add(fragmentWrapper.id, locationsListFragment)
                .commit()
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
        newLocationFab = findViewById(R.id.new_location_fab)
        newLocationFab.setOnClickListener {
            activityStarter.openLocationActionsActivity()
        }
    }
}