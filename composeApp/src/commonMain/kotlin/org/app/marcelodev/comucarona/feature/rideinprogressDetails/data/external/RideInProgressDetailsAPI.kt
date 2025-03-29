package org.app.marcelodev.comucarona.feature.rideinprogressDetails.data.external

import io.ktor.client.*
import io.ktor.client.request.*

interface RideInProgressDetailsAPI {
    suspend fun deleteReservation(riderId: String)
}

class RideInProgressDetailsAPIImpl(
    private val client: HttpClient,
) : RideInProgressDetailsAPI {

    override suspend fun deleteReservation(riderId: String) {
        client.delete("/api/car-ride-reservations/v1/delete-reservation/$riderId")
    }
}