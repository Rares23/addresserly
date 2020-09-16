package ro.crxapps.addresserly.locations.data.models

import com.squareup.moshi.Json
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

open class AddressLocation(
    @PrimaryKey
    var id: Long? = null,
    @field:Json(name = "lat") var lat: Double? = null,
    @field:Json(name = "lng") var lng: Double? = null,
    @field:Json(name = "label") var label: String? = null,
    @field:Json(name = "address") var address: String? = null,
    @field:Json(name = "image") var image: String? = null,

    @Ignore
    var distance: Double? = null

) : RealmObject()