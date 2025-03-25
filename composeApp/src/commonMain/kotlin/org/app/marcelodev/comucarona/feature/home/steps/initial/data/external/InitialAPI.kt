package org.app.marcelodev.comucarona.feature.home.steps.initial.data.external

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*


interface InitialAPI {
    suspend fun getAvailableCarRides(): HttpResponse
}

class InitialAPIImpl(
    private val client: HttpClient
) : InitialAPI {
    override suspend fun getAvailableCarRides(): HttpResponse {
        return client.get("/api/car-ride/v1/available") {
            contentType(ContentType.Application.Json)
        }
    }
}