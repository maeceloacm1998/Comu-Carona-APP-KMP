package org.app.marcelodev.comucarona.commons.usecase.di

import org.app.marcelodev.comucarona.commons.usecase.*
import org.koin.dsl.module

object CommonUseCasesModule {
    val module = module {
        factory { LogoutUseCase(get()) }
        factory { CallPhoneUseCase() }
        factory { CallWhatsappUseCase() }
        factory { ShareLinkUseCase() }
        factory { CopyToClipboardUseCase() }
    }
}