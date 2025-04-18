package com.example.rentalcarapp.model

/*
* This class provides abstraction over the communication channels.
* */
object ServiceManager {

    private lateinit var communicationChannel: CommunicationChannelServices

    private val useFirebase = true

    /*
    * This function will return communication channel based on available cloud service
    * Assuming, primarily firebase channel will use, if firebase having some issue then AWS channel
    * will use
    * */
    fun getCommunicationChannel(): CommunicationChannelServices {
        //Checking dummy condition, with provision of AWS service
        communicationChannel = if (useFirebase) {
            FirebaseService
        } else {
            AwsService
        }
        return communicationChannel
    }
}