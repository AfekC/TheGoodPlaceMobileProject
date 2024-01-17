package com.example.thegoodplaceapp.screens.myCollection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thegoodplaceapp.database.LocationDao
import java.lang.IllegalArgumentException

class MyCollectionViewModelFactory(private val dataSource : LocationDao,
                                   private val application: Application) : ViewModelProvider.Factory {
    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyCollectionListViewModel::class.java)) {
            return MyCollectionListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}