package org.app.marcelodev.comucarona.service.sharedpreferences

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

// Precisamos de uma forma de obter o contexto, pode ser passado via Koin ou manualmente
lateinit var appContext: Context

actual fun provideSettings(): Settings {
    val sharedPreferences = appContext.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
    return SharedPreferencesSettings(sharedPreferences)
}