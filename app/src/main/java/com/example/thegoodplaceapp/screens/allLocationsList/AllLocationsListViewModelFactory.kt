package com.example.thegoodplaceapp.screens.allLocationsList

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thegoodplaceapp.database.LocationDao
import java.lang.IllegalArgumentException

class AllLocationsListViewModelFactory(private val dataSource : LocationDao,
                                       private val application: Application) : ViewModelProvider.Factory {
    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllLocationsListViewModel::class.java)) {
            return AllLocationsListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}