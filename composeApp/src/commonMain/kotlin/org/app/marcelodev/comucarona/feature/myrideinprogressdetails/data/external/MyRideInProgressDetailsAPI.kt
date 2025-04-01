package org.app.marcelodev.comucarona.feature.myrideinprogressdetails.data.external

import io.ktor.client.*
import io.ktor.client.request.*

interface MyRideInProgressDetailsAPI {
    suspend fun deleteCarRide(riderId: String)
    suspend fun deleteReservation(riderId: String)
    suspend fun finishCarRide(riderId: String)
}

class MyRideInProgressDetailsAPIImpl(
    private val client: HttpClient,
) : MyRideInProgressDetailsAPI {

    override suspend fun deleteCarRide(riderId: String) {
        client.put("/api/car-ride/v1/delete/$riderId")
    }

    override suspend fun deleteReservation(riderId: String) {
        client.delete("/api/car-ride-reservations/v1/delete-reservation/$riderId")
    }

    override suspend fun finishCarRide(riderId: String) {
        client.put("/api/car-ride/v1/finish/$riderId")
    }
}