package org.app.marcelodev.comucarona.commons.utils

import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.create
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice

actual class DeviceUtils {
    actual suspend fun getUniqueDeviceId(): String {
        return UIDevice.currentDevice.identifierForVendor?.UUIDString ?: "unknown-device-id"
    }

    actual companion object {
        actual fun create(): DeviceUtils = DeviceUtils()
    }
}

actual class CallPhoneUtils actual constructor() {
    actual fun handleCallPhone(phoneNumber: String, onErrorAction: (message: String) -> Unit) {
        val url = NSURL.URLWithString("tel:$phoneNumber")
        if (url != null && UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication.openURL(url)
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
        val urlString = "https://api.whatsapp.com/send?phone=$phoneNumber&text=${message}"
        val webURL = NSURL.URLWithString(urlString)

        if (webURL != null) {
            UIApplication.sharedApplication.openURL(
                url = webURL,
                options = emptyMap<Any?, Any>(),  // Map vazio para compatibilidade
                completionHandler = { success ->
                    if (!success) {
                        println("Erro ao abrir o Safari com o link: $urlString")
                    }
                }
            )
        } else {
            println("Erro ao criar a URL do WhatsApp Web")
        }
    }

    actual companion object {
        actual fun create(): CallWhatsappUtils = CallWhatsappUtils()
    }
}

actual class ShareUtils actual constructor() {
    actual fun handleShare(link: String, onErrorAction: (message: String) -> Unit) {
        val text = NSString.create(string = link) // Criando um NSString para compartilhar

        val activityViewController = UIActivityViewController(activityItems = listOf(text), applicationActivities = null)
        val controller = UIApplication.sharedApplication.keyWindow?.rootViewController
        controller?.presentViewController(activityViewController, animated = true, completion = null)
    }

    actual companion object {
        actual fun create(): ShareUtils = ShareUtils()
    }

}