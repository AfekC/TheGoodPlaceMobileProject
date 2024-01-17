package com.example.thegoodplaceapp.screens.addLocation

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

class AddLocationViewModel(val database: LocationDao, val application: Application): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val image: MutableLiveData<Int> = MutableLiveData<Int>()
    val name: MutableLiveData<String> = MutableLiveData<String>()
    val city: MutableLiveData<String> = MutableLiveData<String>()
    val description: MutableLiveData<String> = MutableLiveData<String>()

    private val _eventSaved = MutableLiveData<Boolean>()
    val eventSaved: LiveData<Boolean>
        get() = _eventSaved
    init {
        Log.i("AddLocationViewModel", "AddLocationViewModel created")
        name.value = ""
        city.value = ""
        description.value = ""
        image.value = 3
    }

    fun saveLocation() {
        Log.i("AddLocationViewModel", name.value!!)
        uiScope.launch {
            saveInDatabase(Location(0L, name.value!!, city.value!!, description.value!!, image.value!!))
            _eventSaved.value = true
        }
    }

    private suspend fun saveInDatabase(location: Location) {
        return withContext(Dispatchers.IO) {
            database.insert(location)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}