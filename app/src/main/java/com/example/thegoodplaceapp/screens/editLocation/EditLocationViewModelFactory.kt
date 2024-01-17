package com.example.thegoodplaceapp.screens.editLocation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thegoodplaceapp.database.LocationDao
import java.lang.IllegalArgumentException

class EditLocationViewModelFactory(private val dataSource : LocationDao,
                                   private val application: Application) : ViewModelProvider.Factory {
    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditLocationViewModel::class.java)) {
            return EditLocationViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}