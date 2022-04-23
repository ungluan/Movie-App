package com.example.moviesapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()





//        Log.d("Main", "Start runBlocking")
//        runBlocking {
//            launch(Dispatchers.IO) {
//                Log.d("Main", "Start Coroutine with Context IO 1")
//                delay(3000L)
//                Log.d("Main", "End Coroutine with Context IO 1")
//            }
//            launch(Dispatchers.IO) {
//                Log.d("Main", "Start Coroutine with Context IO 2")
//                delay(1000L)
//                Log.d("Main", "End Coroutine with Context IO 2")
//
//            }
//        }
//        Thread.sleep(1000L)
//        Log.d("Main","End runBlocking")
//        val time = measureTimeMillis {
//
//        }
//        runBlocking {
//            launch {
//                delay(200L)
//                println("Task from runBlocking")
//            }
//            coroutineScope {
//                launch {
//                    delay(500L)
//                    println("Task from nested launch")
//                }
//                delay(100L)
//                println("Task from coroutine scope")
//            }
//            println("Coroutine scope is over")
//        }

    }
}