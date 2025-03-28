package org.app.marcelodev.comucarona.feature.createcarride.data.external

import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideRequest
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

interface CreateCarRideAPI {
    suspend fun searchAddress(
        address: String
    ): HttpResponse

    suspend fun createCarRide(
        data: CreateCarRideRequest
    )

    suspend fun getLastCarRide(): HttpResponse
}

class CreateCarRideAPIImpl(
    private val client: HttpClient
) : CreateCarRideAPI {

    override suspend fun searchAddress(address: String): HttpResponse {
        return client.post("/api/location/v1/auto-complete-address?address=$address")
    }

    override suspend fun createCarRide(data: CreateCarRideRequest) {
        client.post("/api/car-ride/v1/create", {
            contentType(ContentType.Application.Json)
            setBody(data)
        })
    }

    override suspend fun getLastCarRide(): HttpResponse {
        return client.get("/api/car-ride/v1/find-last")
    }
}