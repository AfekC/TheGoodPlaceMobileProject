package com.example.thegoodplaceapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://maps.googleapis.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface GpsLocationService {
    @GET("maps/api/geocode/json")
    fun getLocations(@Query("latlng") latlng: String,
                    @Query("language") language: String,
                    @Query("location_type") locationType: String,
                    @Query("key") key: String) : Call<LocationProperty>
}
object GpsLocationApi {
    val retrofitService : GpsLocationService by lazy {
        retrofit.create(GpsLocationService::class.java)
    }

    fun getCityFromLocationProperty(location: LocationProperty): AddressComponents? {
        return location.results.get(0).address_components.find { it.types.contains("locality") }
    }
}
