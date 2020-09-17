package ro.crxapps.addresserly.locations.adapters

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import ro.crxapps.addresserly.locations.data.models.AddressLocation
import ro.crxapps.addresserly.locations.views.LocationItemView
import javax.inject.Inject

class LocationsListAdapter @Inject constructor(private val context: Context) :
    RecyclerView.Adapter<LocationsListAdapter.LocationViewHolder>() {

    private val locations: ArrayList<AddressLocation> = ArrayList()
    private var clickListener: OnLocationClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view: LocationItemView = LocationItemView(context)
        val lp: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        view.layoutParams = lp
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location: AddressLocation = locations[position]
        (holder.itemView as LocationItemView).setContent(location)
        (holder.itemView).card.setOnClickListener {
            clickListener?.onLocationClick(location)
        }
    }

    override fun getItemCount(): Int = locations.size

    fun setLocations(locations: List<AddressLocation>) {
        this.locations.apply {
            clear()
            addAll(locations)
            notifyDataSetChanged()
        }
    }

    fun setOnLocationClickListener(listener: OnLocationClickListener) {
        clickListener = listener
    }

    class LocationViewHolder(view: LocationItemView) : RecyclerView.ViewHolder(view)
    interface OnLocationClickListener {
        fun onLocationClick(location: AddressLocation)
    }
}