package com.example.moviesapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.moviesapp.databinding.ActivityMainBinding
import com.example.moviesapp.feature.login.view.LoginFragmentDirections
import com.example.moviesapp.feature.onboarding.OnBoardingFragmentDirections


class MainActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityMainBinding

    //    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        if (isFinishedOnBoarding()) {
            val action =
                OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment(
                    "",
                    "")
            navController.navigate(action)
        }

    }

    private fun isFinishedOnBoarding(): Boolean {
        val sharedPref =
            this.getSharedPreferences(Constant.ON_BOARDING, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(Constant.ON_BOARDING_FINISHED, false)
    }

}