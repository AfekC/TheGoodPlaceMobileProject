package com.example.thegoodplaceapp.screens.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thegoodplaceapp.MainActivity
import com.example.thegoodplaceapp.database.LocationDao
import java.lang.IllegalArgumentException

class LoginViewModelFactory(private val mainActivity: MainActivity) : ViewModelProvider.Factory {
    @Suppress
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(mainActivity) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}