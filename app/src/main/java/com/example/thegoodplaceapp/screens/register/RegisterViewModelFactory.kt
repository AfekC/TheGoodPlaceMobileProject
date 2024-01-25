package com.example.thegoodplaceapp.screens.register

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thegoodplaceapp.MainActivity
import com.example.thegoodplaceapp.database.LocationDao
import java.lang.IllegalArgumentException

class RegisterViewModelFactory(private val mainActivity: MainActivity) : ViewModelProvider.Factory {
    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(mainActivity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}