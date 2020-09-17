package ro.crxapps.addresserly.locations.activities

import android.os.Bundle
import ro.crxapps.addresserly.R
import ro.crxapps.addresserly.core.activities.BaseActivity
import ro.crxapps.addresserly.locations.fragments.LocationActionsFragment
import javax.inject.Inject

class CreateLocationActivity : BaseActivity() {

    @Inject
    lateinit var locationActionsFragment: LocationActionsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_location)
        getActivityComponent().inject(this)
    }
}