package org.app.marcelodev.comucarona.commons.utils

expect class DeviceUtils {
    suspend fun getUniqueDeviceId(): String

    companion object {
        fun create(): DeviceUtils
    }
}