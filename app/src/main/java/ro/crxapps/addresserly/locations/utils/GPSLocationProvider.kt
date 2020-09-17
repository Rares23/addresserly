package ro.crxapps.addresserly.locations.utils

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import javax.inject.Inject

class GPSLocationProvider @Inject constructor(
    private val activity: Activity,
    private val client: FusedLocationProviderClient
) {

    companion object {
        const val REQUEST_LOCATION_PERMISSION: Int = 1
    }

    private var permissionRequested: Boolean = false

    private fun requestPermission() {
        permissionRequested = true
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }

    fun loadCurrentGPSLocation(onLocationLoadListener: OnCurrentLocationLoadListener?) {
        if (ActivityCompat.checkSelfPermission(
                activity,
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            client.lastLocation
                .addOnSuccessListener {
                    onLocationLoadListener?.getCurrentLocation(it)
                }
        } else {
            if (!permissionRequested) {
                requestPermission()
            }
        }
    }

    interface OnCurrentLocationLoadListener {
        fun getCurrentLocation(currentLocation: Location?)
    }
}