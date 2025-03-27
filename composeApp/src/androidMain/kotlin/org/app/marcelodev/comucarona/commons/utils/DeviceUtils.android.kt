package org.app.marcelodev.comucarona.commons.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
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

actual class CallPhoneUtils actual constructor() {
    actual fun handleCallPhone(phoneNumber: String, onErrorAction: (message: String) -> Unit) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (intent.resolveActivity(appContextDevice.packageManager) != null) {
            appContextDevice.startActivity(intent)
        } else {
            onErrorAction("Erro ao tentar abrir o discador.")
        }
    }

    actual companion object {
        actual fun create(): CallPhoneUtils {
            TODO("Not yet implemented")
        }
    }

}