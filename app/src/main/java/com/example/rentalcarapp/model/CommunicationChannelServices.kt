package com.example.rentalcarapp.model

/*
* Provides cloud services
* */
interface CommunicationChannelServices {

    fun sendNotificationToServer(userId:String,carSpeed:Float)

    fun setMaxSpeedLimit(userId:String)

    fun getMaxSpeedLimit(userId:String):Float

}