package org.app.marcelodev.comucarona.service.di

import com.russhwolf.settings.Settings
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
import org.app.marcelodev.comucarona.service.ktor.AuthPreferences
import org.app.marcelodev.comucarona.service.sharedpreferences.SharedPreferencesBuilder
import org.app.marcelodev.comucarona.service.sharedpreferences.SharedPreferencesBuilderImpl
import org.app.marcelodev.comucarona.service.sharedpreferences.provideSettings
import org.koin.dsl.module

object ServiceModule {
    val module = module {
        single { Json { ignoreUnknownKeys = true } }
        single { pSettings() }
        factory<SharedPreferencesBuilder> { SharedPreferencesBuilderImpl(get()) }
        factory { AuthPreferences(get()) }
        single { provideKtor(get(), get()) }
    }

    private fun provideKtor(json: Json, authPreferences: AuthPreferences) = HttpClient {
        install(ContentNegotiation) {
            // TODO Fix API so it serves application/json
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

    private fun pSettings(): Settings = provideSettings()
}