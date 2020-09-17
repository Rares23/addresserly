package ro.crxapps.addresserly.locations.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import ro.crxapps.addresserly.R
import ro.crxapps.addresserly.core.activities.BaseActivity
import ro.crxapps.addresserly.locations.fragments.LocationActionsFragment
import javax.inject.Inject

class LocationActionsActivity : BaseActivity() {

    @Inject
    lateinit var locationActionsFragment: LocationActionsFragment

    private var createProcess: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_actions)
        getActivityComponent().inject(this)
        setLocationActionsFragment(savedInstanceState)
        initToolbar()
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = if (createProcess) {
            getString(R.string.create_screen)
        } else {
            getString(R.string.update_screen)
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun setLocationActionsFragment(savedInstanceState: Bundle?) {
        createProcess = intent?.extras?.getLong("id") == null

        if (savedInstanceState == null) {
            locationActionsFragment.arguments = intent.extras
            val fragmentWrapper: FrameLayout = findViewById(R.id.fragment_wrapper)
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            fragmentTransaction
                .add(fragmentWrapper.id, locationActionsFragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}