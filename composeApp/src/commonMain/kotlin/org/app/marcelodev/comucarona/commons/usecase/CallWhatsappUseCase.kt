package org.app.marcelodev.comucarona.commons.usecase

import org.app.marcelodev.comucarona.commons.utils.CallWhatsappUtils

class CallWhatsappUseCase {
    operator fun invoke(phoneNumber: String, message: String) {
        val callWhatsapp = CallWhatsappUtils.create()

        callWhatsapp.handleCallWhatsapp(
            phoneNumber = phoneNumber,
            message = message
        )
    }

    companion object {
        const val DEFAULT_MESSAGE_CAR_RIDE =
            "Olá, gostaria de saber mais sobre a carona. Ainda tem vaga?"
    }
}