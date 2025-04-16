package com.example.rentalcarapp.model

/*
* This class is use to manage server communication channels.
* */
object ServiceManager {

    private lateinit var communicationChannel: CommunicationChannelServices

    /*
    * This function will return communication channel based on available cloud service
    * */
    fun getCommunicationChannel(service: String): CommunicationChannelServices {
        //Assuming, primarily firebase channel will use, if firebase having some issue then AWS
        // channel will use
        communicationChannel = if (service == "Firebase") {
            FirebaseService
        } else {
            AwsService
        }
        return communicationChannel
    }
}