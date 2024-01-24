package com.example.thegoodplaceapp.screens.addLocation

import android.app.Application
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thegoodplaceapp.MainActivity
import com.example.thegoodplaceapp.database.LocationDao
import java.lang.IllegalArgumentException

class AddLocationViewModelFactory(private val dataSource : LocationDao,
                                    private val application: Application,
                                    private val mainActivity: MainActivity
): ViewModelProvider.Factory {
    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddLocationViewModel::class.java)) {
            return AddLocationViewModel(dataSource, application, mainActivity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}