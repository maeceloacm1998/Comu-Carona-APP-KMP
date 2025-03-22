package org.app.marcelodev.comucarona.service.di

import kotlinx.serialization.json.Json
import org.app.marcelodev.comucarona.service.ktor.AuthPreferences
import org.app.marcelodev.comucarona.service.sharedpreferences.SharedPreferencesBuilder
import org.app.marcelodev.comucarona.service.sharedpreferences.SharedPreferencesBuilderImpl
import org.app.marcelodev.comucarona.service.sharedpreferences.pvdSettings
import org.app.marcelodev.comucarona.service.ktor.provideKtor
import org.koin.dsl.module

object ServiceModule {
    val module = module {
        single { Json { ignoreUnknownKeys = true } }
        single { pvdSettings() }
        factory<SharedPreferencesBuilder> { SharedPreferencesBuilderImpl(get()) }
        factory { AuthPreferences(get()) }
        single { provideKtor(get(), get()) }
    }
}