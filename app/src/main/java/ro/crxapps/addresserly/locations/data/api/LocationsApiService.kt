package ro.crxapps.addresserly.locations.data.api

import retrofit2.Response
import retrofit2.http.GET

interface LocationsApiService {
    @GET("/mylocations")
    suspend fun listLocation(): LocationsApiResponse
}