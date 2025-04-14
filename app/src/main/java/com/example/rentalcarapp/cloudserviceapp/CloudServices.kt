package com.example.rentalcarapp.cloudserviceapp

/*
* Data class, to hold user booking details
* */
data class BookingDetails(val source:String,val startDate:String, val endDate:String,
                          val startTime:String,val endTime:String, val vehicleType:String)

/*
* This class just simulating cloud services functionality, act as a fleet company portal/firebase portal.
*/
class CloudServices {

    // Max speed limit
    private var maxSpeedLimit:Float? = null

    /*
    * This function will manage user signUp functionality using firebase authentication service(or
    * AWS services)
    */
    fun getUserSignUp(userId: String,password: String,mobile:Int){
        // storing user details in DB(using Firebase - FireStore service/AWS)
        // navigating to login page
        userLogin(userId,password)
    }

    /*
    * This function will manage authentication process using Firebase Authentication service(or AWS)
    */
    fun userLogin(userId:String, password:String){
        //Authenticating user, after successful authentication sending response to client(userapp)
    }

    /*
    * This function is use to get user(renter) booking details and send booking ID
    */
    fun confirmBooking(userId:String, bookingDetails: BookingDetails){
        // storing user booking details in DB(Firebase - FireStore/AWS)
        // Setting max speed limit for given user(renter)
        setMaxSpeedLimit(userId)
        // sending booking confirmation to user(renter) with booking Id using firebase cloud
        // messaging service( or AWS services)
        sendBookingId(userId)
    }

    /**
    * This function is used to set max speed limit for specified user.
    * @param userId, The userID required to set max speed limit
    */
    fun setMaxSpeedLimit(userId:String){
        maxSpeedLimit = 100.0F
    }

    /**
     * This function will return max speed limit for particular user based on userId (using
     * firebase --firebase cloud function/AWS services)
     * @param userId The userId
     * @return float value,The max speed limit
     */
    fun getMaxSpeedLimit(userId:String):Float{
        return maxSpeedLimit!!
    }

    /**
    * Sending booking ID to user using firebase cloud messaging service( or AWS service)
    * @param userId, The userId
    * @return Int, return booking id for the given userId
    * */
    fun sendBookingId(userId: String) :Int{
        return 123
    }

    /*
    * Validating booking id using firebase authentication service(or AWS service)
    * @param bookingId, The booking
    * @param userId, the userId
    * @return String, The Success response
    * */
    fun validateBookingId(bookingId:Int,userId: String):String{
        return if(bookingId == sendBookingId(userId))
            "success"
        else "failed"
    }

    /**
     * This method is use to notify current car speed to admin portal(using Firebase-- firebase cloud
     * function/AWS)
     * @param speed The current car Speed
     **/
    fun notifyCurrentSpeedToServer(speed: Float) {
    }
}