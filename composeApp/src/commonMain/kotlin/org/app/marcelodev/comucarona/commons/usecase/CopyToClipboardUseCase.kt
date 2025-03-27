package org.app.marcelodev.comucarona.commons.usecase

import org.app.marcelodev.comucarona.commons.utils.CopyToClipboardUtils

class CopyToClipboardUseCase {
    operator fun invoke(text: String, label: String = "Copied Text", onAlert: () -> Unit = {}) {
        val clipbaord = CopyToClipboardUtils.create()
        clipbaord.copy(
            text = text,
            label = label
        )
    }
}