package com.example.rentalcarapp.hmiapp

import android.app.AlertDialog
import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.rentalcarapp.cloudserviceapp.CloudServices

/*
* This class is work as a In-Vehicle Infotainment HMI App, will manage vehicle related
* functionality.
*/
class HMIActivity : ComponentActivity() {

    /*
    * This variable is used to Initializing Car class object
    */
    private var car: Car? = null

    /*
    * This variable is used to Initializing CarPropertyManager class object
    */
    private var carPropertyManager: CarPropertyManager? = null

    /*
    * Initializing cloud service
    * */
    private var cloudServices: CloudServices? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //Initializing objects
        initializer(this)
    }

    override fun onResume() {
        super.onResume()
        //Authenticating user before starting rental period.
        userLogin()
    }

    /*
    * This function is use for user login, either user can login using same login credentials or
    * using booking Id(OTP) which is provided by userApp.
    */
    fun userLogin() {
        // For User authentication, use cloud service(firebase Authentication/AWS)
        // Passing userId / booking Id through firebase cloud functions, then firebase cloud service will
        // validating this authentication then sending response - SUCCESS/FAILED
        if (cloudServices!!.userLogin("abc", "abc") == SUCCESS || cloudServices!!.validateBookingId(
                123,
                "abc"
            ) == "Success"
        ) {
            //After successful login,user rental period will start
            registerSpeedListener()
        } else {
            Log.d("HMI App", "userId or booking id is not matched ")
        }
    }

    /*
    * Initializing car object and getting CarPropertyManager instance by passing
    * CarProperty service name.
    * */
    private fun initializer(context: Context) {
        car = Car.createCar(context)
        carPropertyManager = car?.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
        cloudServices = CloudServices()
    }

    /*
    * Register to CarPropertyEventCallback listener which provides onChangeEvent() callback function
    * through which we will getting PropertyId and its value.
    */
    private fun registerSpeedListener() {
        carPropertyManager?.subscribePropertyEvents(VehiclePropertyIds.PERF_VEHICLE_SPEED,
            object : CarPropertyManager.CarPropertyEventCallback {
                override fun onChangeEvent(value: CarPropertyValue<*>?) {
                    if (value?.propertyId == VehiclePropertyIds.PERF_VEHICLE_SPEED) {
                        val currentSpeed = value.value as Float
                        Log.d("RentalCarApp", "Current speed :$currentSpeed ")
                        checkVehicleSpeed(currentSpeed)
                    }
                }

                override fun onErrorEvent(p0: Int, p1: Int) {
                    Log.d("RentalCarApp", "PropertyId not found $p0")
                }
            })
    }

    /**
     * This method will check current car speed with Max speed limit, then send alert to user and car
     * rental company
     * @param speed The current car speed
     */
    fun checkVehicleSpeed(speed: Float) {
        //Below method getMaxSpeedFromServer() will return max speed limit for particular user
        // based on userId from server(using firebase-- firebase cloud function/AWS)
        val maxSpeed: Float = cloudServices!!.getMaxSpeedLimit("UserId")
        // Check current car speed is > Max speed limit which is set by rental fleet company
        if (speed > maxSpeed) {
            //Below method is used to notify current speed to rental company admin portal(Firebase--
            // firebase cloud function/AWS)
            cloudServices!!.notifyCurrentSpeedToServer(speed)
            // Show speed alert message to user(renter)
            showWarningMsg(maxSpeed)
        }
    }

    /*
    * This function will showing waring msg to user when current speed is greater than max speed
    * limit
    * @param maxSpeed The max speed limit
    */
    private fun showWarningMsg(maxSpeed: Float) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Max speed Alert")
        builder.setMessage("Warning: Speed exceeds $maxSpeed km/h!")
        builder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        //Disconnecting car service
        car!!.disconnect()
    }
}