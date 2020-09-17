package ro.crxapps.addresserly.core.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ro.crxapps.addresserly.locations.activities.LocationActionsActivity
import ro.crxapps.addresserly.locations.activities.LocationDetailsActivity
import javax.inject.Inject

class ActivityStarter @Inject constructor(private val context: Context) {
    fun openLocationActionsActivity(locationId: Long? = null) {
        val intent: Intent = Intent(context, LocationActionsActivity::class.java)
        locationId?.let {
            val bundle: Bundle = Bundle()
            bundle.putLong("id", it)
            intent.putExtras(bundle)
        }
        context.startActivity(intent)
    }

    fun openLocationDetailsActivity(locationId: Long) {
        val intent: Intent = Intent(context, LocationDetailsActivity::class.java)
        val bundle: Bundle = Bundle()
        bundle.putLong("id", locationId)
        intent.putExtras(bundle)
        context.startActivity(intent)
    }
}