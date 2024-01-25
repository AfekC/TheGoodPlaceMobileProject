package com.example.thegoodplaceapp.screens.myCollection

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.thegoodplaceapp.database.LocationDao
import com.google.firebase.auth.FirebaseAuth

class MyCollectionListViewModel(val database: LocationDao, val application: Application): ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val locations = database.getMyLocations(auth.currentUser!!.uid)
    init {
        Log.i("AddLocationViewModel", "AllLocationsListViewModel created")
    }
}