package org.app.marcelodev.comucarona.commons.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.AppTrackingTransparency.ATTrackingManager
import platform.AppTrackingTransparency.ATTrackingManagerAuthorizationStatusAuthorized
import platform.UIKit.UIDevice

actual class DeviceUtils {
    actual suspend fun getUniqueDeviceId(): String {
        return UIDevice.currentDevice.identifierForVendor?.UUIDString ?: "unknown-device-id"
    }

    actual companion object {
        actual fun create(): DeviceUtils = DeviceUtils()
    }
}