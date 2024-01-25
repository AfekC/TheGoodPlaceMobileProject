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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.time.LocalDate
import java.time.LocalDateTime

class EditLocationViewModel(val database: LocationDao, val application: Application, val mainActivity: MainActivity): ViewModel() {

    val db = Firebase.firestore
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val locationsImagesRef =  Firebase.storage.reference.child("location")

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
        val locationToFirebase = hashMapOf(
            "name" to location.value!!.name,
            "city" to location.value!!.city,
            "description" to location.value!!.description,
            "creator" to auth.currentUser!!.uid,
            "imageVersion" to LocalDateTime.now().nano
        )

        db.collection("locations")
            .document(location.value!!.locationId)
            .set(locationToFirebase)
            .addOnSuccessListener { documentReference ->
                if (location.value!!.image != null) {
                    locationsImagesRef.child(location.value!!.locationId)
                        .putBytes(location.value!!.image!!)
                }
                Log.d("firebase", "DocumentSnapshot updated with ID: ${location.value!!.locationId}")
                _eventSaved.value = true
            }
            .addOnFailureListener { e ->
                Log.w("firebase", "Error adding document", e)
            }
    }

    fun deleteLocation() {
        db.collection("locations")
            .document(location.value!!.locationId)
            .delete()
            .addOnSuccessListener{
                _eventSaved.value = true
                locationsImagesRef.child(location.value!!.locationId).delete().addOnFailureListener {
                    Log.w("firebase", "location does not have a picture")
                }
            }
            .addOnFailureListener { e ->
                Log.w("firebase", "Error deleting document", e)
            }

    }
}