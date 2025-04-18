package com.example.rentalcarapp.model

/*
* This class provides speed info by making network calls.
* */
object MaxSpeedApis {

    // Max speed limit
    private var maxSpeedLimit:Float? = null

    /**
     *  Set max speed limit for specified user, Assuming maxlimit will set by fleet company before
     *  rental period starts
     * @param userId, The userID required to set max speed limit
     */
      fun setMaxSpeedLimit(userId:String){
        maxSpeedLimit = 100.0F
    }

    /**
     * This function will return max speed limit for particular user based on userId
     * @param userId The userId
     * @return float value,The max speed limit
     */
     fun getMaxSpeedLimit(userId:String):Float{
        return maxSpeedLimit!!
    }
}