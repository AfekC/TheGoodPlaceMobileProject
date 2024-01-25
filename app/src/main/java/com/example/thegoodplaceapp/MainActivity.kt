package com.example.thegoodplaceapp

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.example.thegoodplaceapp.database.LocationDao
import com.example.thegoodplaceapp.database.LocationDatabase
import com.example.thegoodplaceapp.model.Location
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db = Firebase.firestore
    val locationsImagesRef =  Firebase.storage.reference.child("location")

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var locationDao: LocationDao? = null
    var uriResult: MutableLiveData<Uri?> = MutableLiveData<Uri?>()
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            uriResult.value = uri
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            2
        )
        locationDao = LocationDatabase.getInstance(application).locationDao
        uiScope.launch {
            deleteAllFromDatabase()
        }

        db.collection("locations").addSnapshotListener { value, error ->
            if (error != null) {
                Log.w("firebase", "listen:error", error)
                return@addSnapshotListener
            }
            value!!.documentChanges.forEach { documentChange ->
                if (documentChange.type == DocumentChange.Type.ADDED) {
                    val data = documentChange.document.data
                    val location = Location(
                        documentChange.document.id,
                        data["name"] as String,
                        data["city"] as String,
                        data["description"] as String,
                        null,
                        data["creator"] as String
                    )

                    locationsImagesRef.child(documentChange.document.id).getBytes(5*1024*1024).addOnCompleteListener {
                        if (it.isSuccessful) {
                            location.image = it.result
                            uiScope.launch {
                                insertToDatabase(location)
                            }
                        }
                    }.addOnFailureListener {
                        uiScope.launch {
                            insertToDatabase(location)
                        }
                    }
                } else if (documentChange.type == DocumentChange.Type.REMOVED) {
                    uiScope.launch {
                        deleteFromDatabase(documentChange.document.id)
                    }
                } else if (documentChange.type == DocumentChange.Type.MODIFIED) {
                    val data = documentChange.document.data
                    val location = Location(
                        documentChange.document.id,
                        data["name"] as String,
                        data["city"] as String,
                        data["description"] as String,
                        null,
                        data["creator"] as String
                    )

                    locationsImagesRef.child(documentChange.document.id).getBytes(5*1024*1024).addOnCompleteListener {
                        if (it.isSuccessful) {
                            location.image = it.result
                            uiScope.launch {
                                updateInDatabase(location)
                            }
                        }
                    }.addOnFailureListener {
                        uiScope.launch {
                            updateInDatabase(location)
                        }
                    }
                }
            }
        }
    }

    private suspend fun updateInDatabase(location: Location) {
        return withContext(Dispatchers.IO) {
            locationDao!!.update(location)
        }
    }

    private suspend fun deleteFromDatabase(id: String) {
        return withContext(Dispatchers.IO) {
            locationDao!!.delete(locationDao!!.get(id))
        }
    }

    private suspend fun insertToDatabase(location: Location) {
        return withContext(Dispatchers.IO) {
            locationDao!!.insert(location)
        }
    }

    private suspend fun deleteAllFromDatabase() {
        return withContext(Dispatchers.IO) {
            locationDao!!.deleteAll()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelJob.cancel()
        uiScope.launch {
            deleteAllFromDatabase()
        }
    }
}
