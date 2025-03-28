package org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.external

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

interface RideInProgressAPI {
    suspend fun getRideInProgress(
        status: String? = null
    ): HttpResponse

}

class RideInProgressAPImpl(
    private val client: HttpClient
) : RideInProgressAPI {
    override suspend fun getRideInProgress(status: String?): HttpResponse {
        return client.get("/api/car-ride-reservations/v1/my-reservations?status=$status")
    }
}