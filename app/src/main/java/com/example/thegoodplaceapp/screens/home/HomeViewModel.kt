package com.example.thegoodplaceapp.screens.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thegoodplaceapp.network.GpsLocationApi
import com.example.thegoodplaceapp.network.LocationProperty
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Response
import java.util.Locale

class HomeViewModel(val application: Application) : ViewModel() {
    val _response = MutableLiveData<String>()
    val location: LiveData<String>
        get() = _response

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2

    init {
        Log.i("HomeViewModel", "HomeViewModel created")
        _response.value = "Loading..."
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
        getLocation()
    }


    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                application.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                application.applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            _response.value = "אין הרשאות מיקום"
            return
        }
        mFusedLocationClient.flushLocations()
        mFusedLocationClient.getLastLocation().addOnCompleteListener(application.mainExecutor) { task ->
            val location: Location? = task.result
            if (location != null) {
                GpsLocationApi.retrofitService.getLocations(
                    location.latitude.toString() + "," + location.longitude.toString(),
                    "he",
                    "ROOFTOP",
                    "AIzaSyDOnAhK7xf7eHSNmZ-66G0XDqXcbmHI4hE").enqueue(
                    object: retrofit2.Callback<LocationProperty> {
                    override fun onFailure(call: Call<LocationProperty>, t: Throwable) {
                        _response.value = "תקלת תקשורת!"
                    }

                    override fun onResponse(call: Call<LocationProperty>, response: Response<LocationProperty>) {
                        val addressComponent = GpsLocationApi.getCityFromLocationProperty(response.body()!!)
                        _response.value = addressComponent!!.short_name
                    }
                })

            }
        }
    }
}