package org.app.marcelodev.comucarona.commons.utils

import platform.Foundation.NSURL
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