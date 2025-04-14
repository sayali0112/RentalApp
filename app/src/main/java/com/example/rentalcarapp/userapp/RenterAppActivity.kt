package com.example.rentalcarapp.userapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rentalcarapp.R
import com.example.rentalcarapp.cloudserviceapp.BookingDetails
import com.example.rentalcarapp.cloudserviceapp.CloudServices

/*
* This activity class is use to manage user(car renter) related functionality
*/
class RenterAppActivity : AppCompatActivity() {

    /*
    * Initialise CloudServices instance
    * */
    var cloudServices:CloudServices? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_app)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cloudServices = CloudServices()
    }

    override fun onResume() {
        super.onResume()
        userLoginSignup()
    }

    /*
    * This function is use for user signUp/SignIn functionality which is managed by cloud service
    */
    fun userLoginSignup(){
        // Calling firebase authentication service to manage user signIn/SignUp functionality (or
        // use AWS services)
        cloudServices!!.getUserSignUp("abc@gmail.com","password",1234753)
        if(response == SUCCESS){
            // After successfully login, User will navigate to rental car booking screen
            bookRentalCar()
        } else {
            Log.d("RenterApp","Login failed")
        }
    }

    /*
    * This function is use to send user(renter) booking details to cloud database by using firebase cloud
    * function service(or AWS service).
    */
    fun bookRentalCar(){
        // Select source address
        // Select start date with time
        // Select end date with time
        // Select car details(like car type - small/SUV, fuel type - EV/Petrol etc...)
        // Send this booking request with above details to server(rental car company server) with
        // userId using firebase cloud function( or AWS).
          cloudServices!!.confirmBooking("userID", BookingDetails("abc","12/04/2025",
              "13/04/2025","1pm","3pm","EV"))
        // Once booking is done, server will send success response to client with booking ID through
        // firebase cloud messaging (or AWS service).
    }
}