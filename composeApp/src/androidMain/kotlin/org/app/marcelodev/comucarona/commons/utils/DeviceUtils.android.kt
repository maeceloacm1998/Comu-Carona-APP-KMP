package org.app.marcelodev.comucarona.commons.utils

import android.content.Context
import android.provider.Settings


lateinit var appContextDevice: Context

actual class DeviceUtils() {
    actual suspend fun getUniqueDeviceId(): String {
        return Settings.Secure.getString(appContextDevice.contentResolver, Settings.Secure.ANDROID_ID)
    }

    actual companion object {
        actual fun create(): DeviceUtils = DeviceUtils()
    }
}