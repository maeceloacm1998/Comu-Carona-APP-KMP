package org.app.marcelodev.comucarona.feature.home.steps.profile.data.external

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountRequest

interface ProfileAPI {
    suspend fun getProfile(): HttpResponse
    suspend fun updateProfile(
        user: RegisterAccountRequest
    ): HttpResponse
}

class ProfileAPIImpl(
    private val client: HttpClient
) : ProfileAPI {
    override suspend fun getProfile(): HttpResponse {
        return client.get("/api/profile/v1/user-profile") {
            contentType(ContentType.Application.Json)
        }
    }
    override suspend fun updateProfile(user: RegisterAccountRequest): HttpResponse {
        return client.put("/api/profile/v1/user-profile") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
    }
}