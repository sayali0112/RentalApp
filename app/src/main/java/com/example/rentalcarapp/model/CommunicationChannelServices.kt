package com.example.rentalcarapp.model

/*
* Provides cloud services
* */
interface CommunicationChannelServices {

    fun sendNotificationToServer(userId:String,carSpeed:Float)

}