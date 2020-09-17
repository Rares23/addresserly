package ro.crxapps.addresserly.locations.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import ro.crxapps.addresserly.R
import ro.crxapps.addresserly.locations.data.models.AddressLocation

class LocationItemView : FrameLayout {

    var card: CardView
    private val address: TextView
    private val distance: TextView
    private val image: ImageView
    val edit: ImageView

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.location_item_view, this)
        card = findViewById(R.id.card)
        address = findViewById(R.id.address)
        distance = findViewById(R.id.distance)
        image = findViewById(R.id.image)
        edit = findViewById(R.id.edit)
    }

    fun setContent(location: AddressLocation) {
        address.text = location.address ?: context.getString(R.string.unknown_address)
        val locationDistance: Double? = location.distance
        distance.text = if(locationDistance != null) {
            context.getString(R.string.distance, locationDistance.toInt().toString())
        } else {
            context.getString(R.string.invalid_distance)
        }

        if(location.id != null) {
            edit.visibility = View.VISIBLE
        } else {
            edit.visibility = View.GONE
        }

        image.post{
            Glide.with(context)
                .load(location.image)
                .error(R.drawable.noimage)
                .into(image)
        }
    }
}