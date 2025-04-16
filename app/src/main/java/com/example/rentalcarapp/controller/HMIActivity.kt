package com.example.rentalcarapp.controller

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi

/*
* This class is work as a In-Vehicle Infotainment HMI App, will manage vehicle related
* functionality.
*/
@RequiresApi(Build.VERSION_CODES.O)
class HMIActivity : ComponentActivity() {

    @SuppressLint("UnsafeIntentLaunch")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }

    override fun onResume() {
        super.onResume()
        //Authenticating user before starting rental period.
        userAuthentication()
    }

    /*
    * This function is use for user authentication on HMI app, either user can login using login
    * credentials or using booking Id(OTP) which is provided by userApp(RenterApp).
    */
    private fun userAuthentication() {
            //After successful authentication,user rental period will start
            intent = Intent(this, CarPropertyService::class.java)
            startForegroundService(intent)
    }
}