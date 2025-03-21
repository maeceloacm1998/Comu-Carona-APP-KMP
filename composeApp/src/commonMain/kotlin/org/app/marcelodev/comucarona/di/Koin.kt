package org.app.marcelodev.comucarona.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module

fun initKoin(config : KoinAppDeclaration? = {}) {
    startKoin {
        printLogger()
        modules(
            AppModule().module,
        )
        config?.let { it() }
    }
}