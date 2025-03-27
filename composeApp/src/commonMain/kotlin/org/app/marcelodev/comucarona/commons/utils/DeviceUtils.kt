package org.app.marcelodev.comucarona.commons.utils

expect class DeviceUtils {
    suspend fun getUniqueDeviceId(): String

    companion object {
        fun create(): DeviceUtils
    }
}

expect class CallPhoneUtils() {
    fun handleCallPhone(phoneNumber: String, onErrorAction: (message: String) -> Unit = {})

    companion object {
        fun create(): CallPhoneUtils
    }
}

expect class CallWhatsappUtils() {
    fun handleCallWhatsapp(phoneNumber: String, message: String)

    companion object {
        fun create(): CallWhatsappUtils
    }
}

expect class ShareUtils() {
    fun handleShare(link: String, onErrorAction: (message: String) -> Unit = {})

    companion object {
        fun create(): ShareUtils
    }
}

expect class CopyToClipboardUtils() {
    fun copy(text: String, label: String = "Copied Text")

    companion object {
        fun create(): CopyToClipboardUtils
    }
}

