package com.example.thegoodplaceapp.screens.editLocation

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thegoodplaceapp.database.Location
import com.example.thegoodplaceapp.database.LocationDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditLocationViewModel(val database: LocationDao, val application: Application): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val location: MutableLiveData<Location> = MutableLiveData<Location>()

    private val _eventSaved = MutableLiveData<Boolean>()
    val eventSaved: LiveData<Boolean>
        get() = _eventSaved
    init {
        Log.i("AddLocationViewModel", "AddLocationViewModel created")
    }

    fun saveLocation() {
        uiScope.launch {
            saveInDatabase(location.value!!)
            _eventSaved.value = true
        }
    }

    private suspend fun saveInDatabase(location: Location) {
        return withContext(Dispatchers.IO) {
            database.update(location)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}