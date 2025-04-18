package com.example.rentalcarapp.model

import android.util.Log

/*
* Provides AWS cloud services
* */
object AwsService : CommunicationChannelServices {

    /**
     * This method is use to notify current car speed to admin portal,using AWS cloud service
     * @param speed The current car Speed
     **/
    override fun sendNotificationToServer(userId: String, carSpeed: Float) {
         Log.i("AwsService","AWS Notification sent")
    }
}