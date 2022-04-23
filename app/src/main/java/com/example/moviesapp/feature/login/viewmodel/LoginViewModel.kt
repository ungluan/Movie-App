package com.example.moviesapp.feature.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.network.model.AuthResponse
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "LOGIN"

class LoginViewModel : ViewModel() {
    private var _isLoadingProgressBar = MutableLiveData(false)
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    val isLoadingProgressBar: LiveData<Boolean> = _isLoadingProgressBar

//    fun signIn(
//        email: String,
//        password: String,
//        workAfterLoginSuccess: () -> Unit,
//        workAfterLoginFailed: () -> Unit
//    ) {
//        updateStateProgressBar(true)
//        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                // Sign in success, update UI with the signed-in user's information
//                Log.d(TAG, "signInWithEmail:success")
//                workAfterLoginSuccess()
//            } else {
//                // If sign in fails, display a message to the user.
//                Log.w(TAG, "signInWithEmail:failure", task.exception)
//                updateStateProgressBar(false)
//                workAfterLoginFailed()
//            }
//        }
//    }

    private fun updateStateProgressBar(isLoading: Boolean) {
        _isLoadingProgressBar.value = isLoading
    }

    /***
    - Cần clear 1 số vấn đề:
    1. Kiểu trả về là gì? là Deferred<AuthResponse> hay là AuthResponse
    2.
     ***/
    suspend fun signIn(
        email: String,
        password: String,
    ): AuthResponse {
        var authResponse = viewModelScope.async(Dispatchers.IO) {
            try {
                val authResult: AuthResult =
                    Tasks.await(auth.signInWithEmailAndPassword(email, password))
                return@async AuthResponse(false, "")
            } catch (e: Exception) {
                e.printStackTrace()
                val position: Int = e.localizedMessage!!.lastIndexOf(":") + 1
                return@async AuthResponse(true, e.localizedMessage!!.substring(position).trim() )
            }
        }
        return authResponse.await()
    }
}