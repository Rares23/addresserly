package ro.crxapps.addresserly.locations.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ro.crxapps.addresserly.R
import ro.crxapps.addresserly.core.fragments.BaseFragment
import ro.crxapps.addresserly.locations.viewmodels.LocationActionsViewModel
import javax.inject.Inject

class LocationActionsFragment: BaseFragment() {

    @Inject
    lateinit var locationActionsViewModel: LocationActionsViewModel

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_location_actions, container, false)
        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getActivityComponent().inject(this)
    }
}