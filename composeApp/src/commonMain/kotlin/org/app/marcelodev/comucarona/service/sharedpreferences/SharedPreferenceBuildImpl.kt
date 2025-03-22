package org.app.marcelodev.comucarona.service.sharedpreferences

import com.russhwolf.settings.Settings

/**
 * SharedPreferencesBuilderImpl using Multiplatform Settings
 */
class SharedPreferencesBuilderImpl(private val settings: Settings) : SharedPreferencesBuilder {

    override fun putString(key: String, data: String) {
        settings.putString(key, data)
    }

    override fun putBoolean(key: String, data: Boolean) {
        settings.putBoolean(key, data)
    }

    override fun getString(key: String, defaultValue: String?): String? {
        return settings.getString(key, defaultValue ?: "")
    }

    override fun getBoolean(key: String, defaultValue: Boolean?): Boolean {
        return settings.getBoolean(key, defaultValue ?: false)
    }
}