package org.app.marcelodev.comucarona.feature.myrideinprogressdetails.data.external

import io.ktor.client.*
import io.ktor.client.request.*

interface MyRideInProgressDetailsAPI {
    suspend fun deleteCarRide(riderId: String)
}

class MyRideInProgressDetailsAPIImpl(
    private val client: HttpClient,
) : MyRideInProgressDetailsAPI {

    override suspend fun deleteCarRide(riderId: String) {
        client.delete("/api/car-ride/v1/delete/$riderId")
    }
}