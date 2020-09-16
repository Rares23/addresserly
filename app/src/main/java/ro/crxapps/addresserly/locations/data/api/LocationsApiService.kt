package ro.crxapps.addresserly.locations.data.api

import retrofit2.Response
import retrofit2.http.GET
import ro.crxapps.addresserly.locations.data.models.Location

interface LocationsApiService {
    @GET("/mylocations")
    suspend fun listLocation(): Response<List<Location>>
}