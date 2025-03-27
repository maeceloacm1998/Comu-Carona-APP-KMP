package org.app.marcelodev.comucarona.commons.usecase

import org.app.marcelodev.comucarona.commons.utils.CallPhoneUtils

class CallPhoneUseCase() {
    operator fun invoke(
        phoneNumber: String, onErrorAction: (message: String) -> Unit = {}
    ) {
        val radialPhoneCall = CallPhoneUtils.create()

        radialPhoneCall.handleCallPhone(
            phoneNumber = phoneNumber,
            onErrorAction = onErrorAction
        )
    }

    companion object {
        private const val ERROR_MESSAGE = "Erro ao tentar abrir o discador."
    }
}