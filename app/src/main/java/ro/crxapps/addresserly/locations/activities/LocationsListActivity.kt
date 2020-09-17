package ro.crxapps.addresserly.locations.activities

import android.os.Bundle
import android.widget.FrameLayout
import ro.crxapps.addresserly.R
import ro.crxapps.addresserly.core.activities.BaseActivity
import ro.crxapps.addresserly.locations.fragments.LocationsListFragment
import javax.inject.Inject


class LocationsListActivity : BaseActivity() {

    @Inject
    lateinit var locationsListFragment: LocationsListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations_list)
        getActivityComponent().inject(this)
        setLocationsListFragment(savedInstanceState)
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

}