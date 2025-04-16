package com.example.rentalcarapp.controller

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.rentalcarapp.model.ServiceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/*
* This service class is used to show alert message when vehicle speed exceeds max limit and also
* notify to the rental company.
* */
class CarPropertyService : Service() {

    /*
   * This variable is used to Initializing Car class object
   */
    private var car: Car? = null

    /*
    * This variable is used to Initializing CarPropertyManager class object
    */
    private var carPropertyManager: CarPropertyManager? = null

    override fun onCreate() {
        super.onCreate()
        initializer(this)
    }

    /*
    * Initializing car object and getting CarPropertyManager instance by passing
    * CarProperty service name.
    * */
    private fun initializer(context: Context) {
        car = Car.createCar(context)
        carPropertyManager = car?.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
    }

    override fun onBind(intent: Intent): IBinder {
        TODO()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //Calling Car Apis on separate thread
        GlobalScope.launch(Dispatchers.IO) {
            registerSpeedListener()
        }
        startForegroundService()
        Log.d("MY ..Service", "running")
        return START_NOT_STICKY
    }

    /*
    * Register to CarPropertyEventCallback listener which provides onChangeEvent() callback function
    * through which we will getting PropertyId and its value.
    */
    private fun registerSpeedListener() {
        carPropertyManager?.subscribePropertyEvents(
            VehiclePropertyIds.PERF_VEHICLE_SPEED,
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
     * This method will check current car speed with Max speed limit, then send alert msg to user and car
     * rental company
     * @param speed The current car speed
     */
    fun checkVehicleSpeed(speed: Float) {
        //Below method getMaxSpeedFromServer() will return max speed limit for particular user
        // based on userId from server(using firebase-- firebase cloud function)
        val maxSpeed: Float =
            ServiceManager.getCommunicationChannel("Firebase").getMaxSpeedLimit("UserId")
        if (speed > maxSpeed) {
            //Below method is used to notify current speed to rental company admin portal(Firebase--
            // firebase cloud function/AWS)
            ServiceManager.getCommunicationChannel("Firebase").sendNotificationToServer("userid", speed)
            // Show speed alert message to user(renter)
            showWarningMsg(maxSpeed)
        }
    }

    /*
    * This function will showing waring msg to user when current speed exceeds max limit by starting
    * popup activity
    * @param carSpeed The max speed limit
    */
    private fun showWarningMsg(carSpeed: Float) {
        val intent = Intent(this, AlertPopupActivity::class.java).apply {
            putExtra("carspeed", carSpeed)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
    }

    /*
    * This function, Starts foreground service with notification
    * */
    private fun startForegroundService() {
        createNotificationChannel()
        val notification = createNotification()
        startForeground(123, notification)
    }

    /*
    * Create notification
    * */
    private fun createNotification(): Notification {
        val notificationIntent = Intent(this, HMIActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(this, "my_channel_id")
            .setContentTitle("Car Property Service")
            .setContentText("Service is running...")
            .setContentIntent(pendingIntent)
            .build()
    }

    /*
    * Create notification channel
    * */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "my_channel_id",
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        car!!.disconnect()
    }
}