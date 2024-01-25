package com.example.thegoodplaceapp.screens.register

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thegoodplaceapp.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegisterViewModel(val mainActivity: MainActivity): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val name: MutableLiveData<String> = MutableLiveData<String>()
    val email: MutableLiveData<String> = MutableLiveData<String>()
    val password: MutableLiveData<String> = MutableLiveData<String>()

    private val _eventSaved = MutableLiveData<Boolean>()
    val eventSaved: LiveData<Boolean>
        get() = _eventSaved


    init {
        name.value = ""
        email.value = ""
        password.value = ""
    }

    fun register() {
        if (email.value!!.length!! < 5) {
            Toast.makeText(mainActivity.applicationContext, "email is to short", Toast.LENGTH_SHORT).show()
            return
        }
        if (name.value!!.length!! < 3) {
            Toast.makeText(mainActivity.applicationContext, "name is to short", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.value!!.length!! < 4) {
            Toast.makeText(mainActivity.applicationContext, "password is to short", Toast.LENGTH_SHORT).show()
            return
        }
        uiScope.launch {
            auth.createUserWithEmailAndPassword(email.value!!, password.value!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Registration successful
                        var user = auth.currentUser
                        if (user != null) {
                            val profileUpdates =
                                UserProfileChangeRequest.Builder().setDisplayName(name.value)
                                    .build()
                            user.updateProfile(profileUpdates).addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    Log.i("Registration", "createUserWithEmail:success")
                                    _eventSaved.value = true
                                }
                            }
                        }
                    } else {
                        // Registration failed
                        Log.w("Registration", "createUserWithEmail:failure", task.exception)
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}