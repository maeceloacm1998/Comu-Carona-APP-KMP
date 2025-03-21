package org.app.marcelodev.comucarona.di

import org.app.marcelodev.comucarona.GameModule
import org.app.marcelodev.comucarona.service.di.ServiceModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initKoin(config : KoinAppDeclaration? = null) {
    startKoin {
        printLogger()
        includes(config)
        modules(listOf(
            ServiceModule.module,
            GameModule.module
        ))
    }
}