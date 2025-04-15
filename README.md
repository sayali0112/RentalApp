# RentalApp
Car Rental App 
Assuming rental company, managing its own fleet.

1.RenterAppActivity --> Manage renter functionality like renter signup/signin, rental car booking 
2.HMIActivity --> It represents In-Vehicle infoitainment app, which check takes bookingId before starting rental period, then checking 
current car speed continuesly and compared with max speed limit which is set fleet company. If it is exceeds then alert renter and notifying
rental company.
3.CloudServices --> This class just simulating cloud services functionality, act as a fleet company portal(Firebase/AWS portal).
