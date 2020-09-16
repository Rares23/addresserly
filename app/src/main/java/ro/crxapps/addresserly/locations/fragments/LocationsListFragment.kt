package ro.crxapps.addresserly.locations.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ro.crxapps.addresserly.R
import ro.crxapps.addresserly.core.activities.BaseActivity
import ro.crxapps.addresserly.core.fragments.BaseFragment
import ro.crxapps.addresserly.core.fragments.FragmentTags
import ro.crxapps.addresserly.locations.viewmodels.LocationsListViewModel
import javax.inject.Inject

class LocationsListFragment : BaseFragment() {

    @Inject
    lateinit var locationsListViewModel: LocationsListViewModel

    companion object {
        const val TAG: String = FragmentTags.LOCATIONS_LIST
    }

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_locations_list, container, false)
        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(activity is BaseActivity) {
            (activity as BaseActivity).getPresentationComponent().inject(this)
        }
    }
}