package org.app.marcelodev.comucarona.service.ktor

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.append
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun provideKtor(json: Json, authPreferences: AuthPreferences) = HttpClient {
    install(ContentNegotiation) {
        json(json, contentType = ContentType.Any)
    }

    // Interceptor e baseUrl
    install(DefaultRequest) {
        url("http://10.0.2.2:8080")

        headers {
            append(HttpHeaders.ContentType, ContentType.Application.Json)
            authPreferences.accessToken?.let { token ->
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    // Logging of all requests
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println("AuthHttpClient Log: $message")
            }
        }
        level = LogLevel.ALL
    }

    // Timeout of requisitions
    install(HttpTimeout) {
        requestTimeoutMillis = 30_000
        connectTimeoutMillis = 30_000
        socketTimeoutMillis = 30_000
    }
}