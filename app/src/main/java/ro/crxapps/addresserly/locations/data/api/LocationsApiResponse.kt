package ro.crxapps.addresserly.locations.data.api

import com.squareup.moshi.Json
import ro.crxapps.addresserly.locations.data.models.AddressLocation

data class LocationsApiResponse(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "locations") val locations: List<AddressLocation>
)