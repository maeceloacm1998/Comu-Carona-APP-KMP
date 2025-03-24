package org.app.marcelodev.comucarona.di

import org.app.marcelodev.comucarona.GameModule
import org.app.marcelodev.comucarona.commons.usecase.di.CommonUseCasesModule
import org.app.marcelodev.comucarona.feature.checkcode.data.di.CheckCodeModule
import org.app.marcelodev.comucarona.feature.registeraccount.data.di.RegisterAccountModule
import org.app.marcelodev.comucarona.service.di.ServiceModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        printLogger()
        includes(config)
        modules(
            listOf(
                CommonUseCasesModule.module,
                ServiceModule.module,
                CheckCodeModule.module,
                RegisterAccountModule.module,
                GameModule.module
            )
        )
    }
}