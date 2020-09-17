package ro.crxapps.addresserly.core.di.presentation

import android.app.Activity
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import ro.crxapps.addresserly.locations.adapters.LocationsListAdapter
import ro.crxapps.addresserly.locations.fragments.LocationDetailsFragment
import ro.crxapps.addresserly.locations.fragments.LocationsListFragment
import ro.crxapps.addresserly.locations.utils.GPSLocationProvider

@Module
class ActivityModule(private val activity: Activity) {
    @ActivityScope
    @Provides
    fun provideLocationsListFragment() : LocationsListFragment {
        return LocationsListFragment()
    }

    @ActivityScope
    @Provides
    fun provideLocationDetailsFragment() : LocationDetailsFragment {
        return LocationDetailsFragment()
    }

    @ActivityScope
    @Provides
    fun provideRecyclerViewLinearLayoutManager() : LinearLayoutManager {
        return LinearLayoutManager(activity)
    }

    @ActivityScope
    @Provides
    fun provideLocationsListAdapter(): LocationsListAdapter {
        return LocationsListAdapter(activity)
    }

    @ActivityScope
    @Provides
    fun provideFusedLocationProviderClient() : FusedLocationProviderClient {
        return FusedLocationProviderClient(activity)
    }

    @ActivityScope
    @Provides
    fun provideGPSLocationProvider(client: FusedLocationProviderClient) : GPSLocationProvider {
        return GPSLocationProvider(activity, client)
    }

    @ActivityScope
    @Provides
    fun provideSnapHelper(): LinearSnapHelper {
        return LinearSnapHelper()
    }
}