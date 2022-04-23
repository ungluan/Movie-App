package com.example.moviesapp.feature.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.network.model.AuthResponse
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

private const val TAG = "SignUp"

class SignUpViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun createAccount(
        email: String,
        password: String
    ): AuthResponse {
        val authResponse = viewModelScope.async(Dispatchers.IO) {
            try{
                Tasks.await(auth.createUserWithEmailAndPassword(email,password))
                return@async AuthResponse(false)
            }catch (e: Exception){
                e.printStackTrace()
                val position = e.localizedMessage!!.lastIndexOf(":")+1
                return@async AuthResponse(true,e.localizedMessage!!.substring(position).trim())
            }
        }
        return authResponse.await()
    }
}