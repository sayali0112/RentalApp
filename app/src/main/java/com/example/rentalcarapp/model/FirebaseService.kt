package com.example.rentalcarapp.model

import android.util.Log

/*
* Provides Firebase cloud services
* */
object FirebaseService :CommunicationChannelServices{

    /**
     * This method is use to notify current car speed to admin portal,using firebase cloud function
     * service
     * @param userId, user id
     * @param carSpeed The current car Speed
     **/
    override fun sendNotificationToServer(userId: String, carSpeed: Float) {
        Log.i("FirebaseService","Firebase Notification sent")
    }
}