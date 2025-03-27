package org.app.marcelodev.comucarona.commons.utils

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast


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
        actual fun create(): CallPhoneUtils = CallPhoneUtils()
    }

}

actual class CallWhatsappUtils actual constructor() {
    actual fun handleCallWhatsapp(phoneNumber: String, message: String) {
        val url = getUrl(phoneNumber, message)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        if (intent.resolveActivity(appContextDevice.packageManager) != null) {
            appContextDevice.startActivity(intent)
        } else {
            openWithBrowser(url)
        }
    }

    actual companion object {
        actual fun create(): CallWhatsappUtils = CallWhatsappUtils()
    }

    private fun openWithBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        appContextDevice.startActivity(browserIntent)
    }

    private fun getUrl(phoneNumber: String, message: String): String {
        return "https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}"
    }
}

actual class ShareUtils actual constructor() {
    actual fun handleShare(link: String, onErrorAction: (message: String) -> Unit) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, link)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (intent.resolveActivity(appContextDevice.packageManager) != null) {
            appContextDevice.startActivity(intent)
        } else {
            onErrorAction("Erro ao tentar abrir o compartilhamento.")
        }
    }

    actual companion object {
        actual fun create(): ShareUtils = ShareUtils()
    }

}

actual class CopyToClipboardUtils actual constructor() {
    @SuppressLint("ServiceCast")
    actual fun copy(text: String, label: String) {
        val clipboard = appContextDevice.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(appContextDevice, "Texto copiado!", Toast.LENGTH_SHORT).show()
    }

    actual companion object {
        actual fun create(): CopyToClipboardUtils = CopyToClipboardUtils()
    }

}