package ro.crxapps.addresserly.locations.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ro.crxapps.addresserly.R
import ro.crxapps.addresserly.locations.data.models.AddressLocation

class LocationItemView : FrameLayout {

    private val address: TextView
    private val distance: TextView
    private val image: ImageView

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.location_item_view, this)
        address = findViewById(R.id.address)
        distance = findViewById(R.id.distance)
        image = findViewById(R.id.image)
    }

    fun setContent(location: AddressLocation) {
        address.text = location.address ?: context.getString(R.string.unknown_address)
        val distanceKM: Int? = location.distance?.toInt()
        distance.text = if(distanceKM != null) {
            context.getString(R.string.distance, distanceKM.toString())
        } else {
            context.getString(R.string.invalid_distance)
        }
        image.post{
            Glide.with(context)
                .load(location.image)
                .error(R.drawable.noimage)
                .into(image)
        }
    }
}