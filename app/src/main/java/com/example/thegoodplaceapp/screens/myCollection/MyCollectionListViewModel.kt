package com.example.thegoodplaceapp.screens.myCollection

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.thegoodplaceapp.database.LocationDao

class MyCollectionListViewModel(val database: LocationDao, val application: Application): ViewModel() {

    val locations = database.getAllLocations()
    init {
        Log.i("AddLocationViewModel", "AllLocationsListViewModel created")
    }
}