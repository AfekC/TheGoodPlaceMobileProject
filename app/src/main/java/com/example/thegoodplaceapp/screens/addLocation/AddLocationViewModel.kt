package com.example.thegoodplaceapp.screens.addLocation

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
import java.time.LocalDateTime

class AddLocationViewModel(val database: LocationDao, val application: Application, val mainActivity: MainActivity): ViewModel() {

    val db = Firebase.firestore
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val locationsImagesRef =  Firebase.storage.reference.child("location")

    val image: MutableLiveData<ByteArray?> = MutableLiveData<ByteArray?>()
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
        image.value = null
    }

    fun takePhoto() {
        mainActivity.pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    fun saveLocation() {
        val location = hashMapOf(
            "name" to name.value,
            "city" to city.value,
            "description" to description.value,
            "creator" to auth.currentUser!!.uid
        )

        db.collection("locations")
            .add(location)
            .addOnSuccessListener { documentReference ->
                if (image.value != null) {
                    locationsImagesRef.child(documentReference.id).putBytes(image.value!!).addOnSuccessListener {
                        val location = hashMapOf(
                            "imageVersion" to LocalDateTime.now().nano
                        )
                        db.collection("locations").document(documentReference.id).set(location, SetOptions.merge())
                    }
                }
                Log.d("firebase", "DocumentSnapshot added with ID: ${documentReference.id}")
                _eventSaved.value = true
            }
            .addOnFailureListener { e ->
                Log.w("firebase", "Error adding document", e)
            }
    }
}