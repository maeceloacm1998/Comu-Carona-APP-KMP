package org.app.marcelodev.comucarona.service.ktor

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.cache.storage.*
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
        url("https://8cb6-2804-14c-5bc5-855c-1104-2967-8ca3-969a.ngrok-free.app")

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

    install(HttpCache) {
        // Define uma política de cache que sempre busca novos dados
        publicStorage(CacheStorage.Disabled)
    }
}