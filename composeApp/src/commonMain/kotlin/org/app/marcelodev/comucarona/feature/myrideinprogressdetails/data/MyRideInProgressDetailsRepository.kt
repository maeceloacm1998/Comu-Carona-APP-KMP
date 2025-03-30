package org.app.marcelodev.comucarona.feature.myrideinprogressdetails.data

interface MyRideInProgressDetailsRepository {
    suspend fun deleteCarRide(riderId: String): Result<Unit>
    suspend fun finishCarRide(riderId: String): Result<Unit>
}