package com.example.rentalcarapp.controller.hmiapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.example.rentalcarapp.model.CloudServices

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
        userLogin()
    }

    /*
    * This function is use for user login, either user can login using same login credentials or
    * using booking Id(OTP) which is provided by userApp.
    */
    private fun userLogin() {
        // For User authentication, use cloud service(firebase Authentication/AWS)
        // Passing userId / booking Id through firebase cloud functions, then firebase cloud service will
        // validating this authentication then sending response - SUCCESS/FAILED
        if (CloudServices.userLogin("abc", "abc") == SUCCESS || CloudServices.validateBookingId(
                123,
                "abc"
            ) == "Success"
        ) {
            //After successful login,user rental period will start
            intent = Intent(this, CarPropertyService::class.java)
            startForegroundService(intent)
        } else {
            Log.d("HMI App", "userId or booking id is not matched ")
        }
    }
}