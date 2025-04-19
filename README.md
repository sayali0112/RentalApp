# RentalApp
Car Rental App 
Assuming rental company, managing its own fleet.

Controller Module:-

1. HMIActivity.kt :- It is responsible for starting rental session,by assuming bookingID is validated. Then started foreground service.
2. CarPropertyService.kt :- It is responsible for showing alert popup based on current speed and max speed limit.
3. AlertPopupActivity.kt :- Showing alert message to user.

Model Module:-

1. AwsService.kt :- It provides AWS cloud services.
2. FirebaseService.kt :- It provides Firebase cloud services.
3. CommunicationChannelServices.kt :- It act as interface, which provides communication channel based on implementation.
4. ServiceManager.kt :- Provides abstraction over the communication channel.
5. MaxSpeedApis :- Provides max speed by making network calls.
