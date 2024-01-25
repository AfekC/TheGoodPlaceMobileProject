package com.example.thegoodplaceapp.screens.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thegoodplaceapp.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(val mainActivity: MainActivity): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val email: MutableLiveData<String> = MutableLiveData<String>()
    val password: MutableLiveData<String> = MutableLiveData<String>()

    private val _eventSaved = MutableLiveData<Boolean>()
    val eventSaved: LiveData<Boolean>
        get() = _eventSaved


    init {
        email.value = ""
        password.value = ""
    }

    fun login() {
        if (email.value!!.length!! < 5) {
            Toast.makeText(mainActivity.applicationContext, "email is to short", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.value!!.length!! < 4) {
            Toast.makeText(mainActivity.applicationContext, "password is to short", Toast.LENGTH_SHORT).show()
            return
        }
        uiScope.launch {
            auth.signInWithEmailAndPassword(email.value!!, password.value!!)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Registration successful
                        var user = auth.currentUser
                        if (user != null) {
                            Log.i("Login", "signInWithEmailAndPassword:success")
                            _eventSaved.value = true
                        }
                    } else {
                        Toast.makeText(mainActivity.applicationContext, "Error while login", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}