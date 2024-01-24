package com.example.thegoodplaceapp.screens.editLocation

import android.app.Application
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thegoodplaceapp.MainActivity
import com.example.thegoodplaceapp.model.Location
import com.example.thegoodplaceapp.database.LocationDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditLocationViewModel(val database: LocationDao, val application: Application, val mainActivity: MainActivity): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val location: MutableLiveData<Location> = MutableLiveData<Location>()

    private val _eventSaved = MutableLiveData<Boolean>()
    val eventSaved: LiveData<Boolean>
        get() = _eventSaved
    init {
        Log.i("AddLocationViewModel", "AddLocationViewModel created")
    }

    fun takePhoto() {
        mainActivity.pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    fun saveLocation() {
        uiScope.launch {
            saveInDatabase(location.value!!)
            _eventSaved.value = true
        }
    }

    fun deleteLocation() {
        uiScope.launch {
            deleteFromDatabase(location.value!!)
            _eventSaved.value = true
        }
    }

    private suspend fun saveInDatabase(location: Location) {
        return withContext(Dispatchers.IO) {
            database.update(location)
        }
    }

    private suspend fun deleteFromDatabase(location: Location) {
        return withContext(Dispatchers.IO) {
            database.delete(location)
        }
    }

    private suspend fun deleteFromDatabase(location: Location) {
        return withContext(Dispatchers.IO) {
            database.delete(location)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}