package org.app.marcelodev.comucarona.di

import org.app.marcelodev.comucarona.commons.usecase.di.CommonUseCasesModule
import org.app.marcelodev.comucarona.feature.checkcode.data.di.CheckCodeModule
import org.app.marcelodev.comucarona.feature.home.di.HomeModule
import org.app.marcelodev.comucarona.feature.home.steps.initial.data.di.InitialMdules
import org.app.marcelodev.comucarona.feature.home.steps.profile.data.di.ProfileModule
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
                HomeModule.module,
                InitialMdules.module,
                ProfileModule.module
            )
        )
    }
}