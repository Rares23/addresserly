package ro.crxapps.addresserly.core.di.application

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ro.crxapps.addresserly.locations.data.api.LocationsApiService
import javax.inject.Singleton

@Module
class ApiServicesModule {
    private val baseUrl: String = "https://demo1042273.mockable.io/"

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideLocationsApiService(retrofit: Retrofit) : LocationsApiService {
        return retrofit.create(LocationsApiService::class.java)
    }
}