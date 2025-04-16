package com.example.rentalcarapp.model

/*
* Provides AWS cloud services
* */
class AwsService : CommunicationChannelServices {

    // Max speed limit
    private var maxSpeedLimit:Float? = null

    /**
     * This method is use to notify current car speed to admin portal,using AWS cloud service
     * @param speed The current car Speed
     **/
    override fun sendNotificationToServer(userId: String, carSpeed: Float) {

    }

    /**
     * This function is used to set max speed limit for specified user.
     * @param userId, The userID required to set max speed limit
     */
    override  fun setMaxSpeedLimit(userId:String){
        maxSpeedLimit = 100.0F
    }

    /**
     * This function will return max speed limit for particular user based on userId, using AWS
     * cloud service
     * @param userId The userId
     * @return float value,The max speed limit
     */
    override fun getMaxSpeedLimit(userId:String):Float{
        return maxSpeedLimit!!
    }

}