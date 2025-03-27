package org.app.marcelodev.comucarona.commons.usecase.di

import org.app.marcelodev.comucarona.commons.usecase.CallPhoneUseCase
import org.app.marcelodev.comucarona.commons.usecase.LogoutUseCase
import org.koin.dsl.module

object CommonUseCasesModule {
    val module = module {
        factory { LogoutUseCase(get()) }
        factory { CallPhoneUseCase() }
    }
}