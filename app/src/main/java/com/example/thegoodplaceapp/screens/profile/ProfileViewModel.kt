package com.example.thegoodplaceapp.screens.profile

import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thegoodplaceapp.MainActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(val mainActivity: MainActivity): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val profilesImagesRef =  Firebase.storage.reference.child("profile")

    val name: MutableLiveData<String> = MutableLiveData<String>()
    val image: MutableLiveData<ByteArray?> = MutableLiveData<ByteArray?>()

    private val _eventSaved = MutableLiveData<Boolean>()
    private val _logout = MutableLiveData<Boolean>()

    val logout: LiveData<Boolean>
        get() = _logout
    val eventSaved: LiveData<Boolean>
        get() = _eventSaved


    init {
        name.value = auth.currentUser!!.displayName
    }

    fun initImage() {
        profilesImagesRef.child(auth.currentUser!!.email!!).getBytes(5*1024*1024).addOnCompleteListener {
            if (it.isSuccessful) {
                image.value = it.result

            }
        }.addOnFailureListener {  }
    }

    fun updateUserDetails() {
        if (name.value?.length!! < 3) {
            Toast.makeText(mainActivity.applicationContext, "name is to short", Toast.LENGTH_SHORT).show()
            return
        }
        uiScope.launch {
            updateUserNameInFirebase()
            if (image.value != null) {
                updateUserImageInFireBase()
            }
        }
        _eventSaved.value = true
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        _logout.value = true
    }

    fun takePhoto() {
        mainActivity.pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private suspend fun updateUserNameInFirebase() {
        return withContext(Dispatchers.IO) {
            var user = auth.currentUser
            if (user != null) {
                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name.value).build()
                user.updateProfile(profileUpdates).addOnCompleteListener { task2 ->
                    if (task2.isSuccessful) {
                        Log.i("EditProfile", "setDisplayName:success")
                    }
                }
            }
        }
    }

    private fun updateUserImageInFireBase() {
        profilesImagesRef.child(auth.currentUser!!.email!!).putBytes(image.value!!)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}