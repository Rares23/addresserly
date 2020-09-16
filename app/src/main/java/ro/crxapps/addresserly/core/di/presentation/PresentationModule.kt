package ro.crxapps.addresserly.core.di.presentation

import android.content.Context
import dagger.Module
import dagger.Provides
import ro.crxapps.addresserly.locations.fragments.LocationsListFragment

@Module
class PresentationModule(val context: Context) {
    @PresentationScope
    @Provides
    fun provideLocationsListFragment() : LocationsListFragment {
        return LocationsListFragment()
    }
}