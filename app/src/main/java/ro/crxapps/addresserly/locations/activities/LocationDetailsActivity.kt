package ro.crxapps.addresserly.locations.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.CollapsingToolbarLayout
import ro.crxapps.addresserly.R
import ro.crxapps.addresserly.core.activities.BaseActivity
import ro.crxapps.addresserly.locations.fragments.LocationDetailsFragment
import javax.inject.Inject

class LocationDetailsActivity : BaseActivity() {

    @Inject
    lateinit var locationDetailsFragment: LocationDetailsFragment

    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    private var image: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_details)
        getActivityComponent().inject(this)
        initToolbar()
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
        locationDetailsFragment.setHeaderImage(image)
    }

    private fun initToolbar() {
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout)
        image = collapsingToolbarLayout.findViewById(R.id.image)
        val toolbar: Toolbar = collapsingToolbarLayout.findViewById(R.id.toolbar)

        collapsingToolbarLayout.title = getString(R.string.details_screen)

        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}