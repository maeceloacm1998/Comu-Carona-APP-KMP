package org.app.marcelodev.comucarona.feature.carridedetails.data.external

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

interface CarRideDetailsAPI {
    suspend fun getCarRideDetails(
        id: String
    ): HttpResponse

    suspend fun reservationRide(
        riderId: String
    )
}

class CarRideDetailsAPIImpl(
    private val client: HttpClient
) : CarRideDetailsAPI {
    override suspend fun getCarRideDetails(id: String): HttpResponse {
        return client.get("/api/car-ride/v1/details/$id")
    }

    override suspend fun reservationRide(riderId: String) {
        client.post("/api/car-ride/v1/details/reservationRide/$riderId") {
            contentType(ContentType.Application.Json)
            setBody(riderId)
        }
    }
}