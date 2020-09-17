package ro.crxapps.addresserly.locations.activities

import android.os.Bundle
import android.widget.FrameLayout
import ro.crxapps.addresserly.R
import ro.crxapps.addresserly.core.activities.BaseActivity
import ro.crxapps.addresserly.locations.fragments.LocationDetailsFragment
import javax.inject.Inject

class LocationDetailsActivity : BaseActivity() {

    @Inject
    lateinit var locationDetailsFragment: LocationDetailsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_details)
        getActivityComponent().inject(this)
        setLocationDetailsFragment(savedInstanceState)
    }

    private fun setLocationDetailsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            locationDetailsFragment.arguments = intent.extras
            val fragmentWrapper: FrameLayout = findViewById(R.id.fragment_wrapper)
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            fragmentTransaction
                .add(fragmentWrapper.id, locationDetailsFragment)
                .commit()
        }
    }
}