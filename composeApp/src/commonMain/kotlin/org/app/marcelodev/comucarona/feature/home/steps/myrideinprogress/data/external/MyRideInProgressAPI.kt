package org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.data.external

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

interface MyRideInProgressAPI {
    suspend fun getMyRideInProgress(
        status: String? = null
    ): HttpResponse
}

class MyRideInProgressAPImpl(
    private val client: HttpClient
) : MyRideInProgressAPI {
    override suspend fun getMyRideInProgress(status: String?): HttpResponse {
        return client.get("/api/car-ride-reservations/v1/my-car-rides?status=$status")
    }
}