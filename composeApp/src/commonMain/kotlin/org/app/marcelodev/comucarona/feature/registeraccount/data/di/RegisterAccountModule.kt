package org.app.marcelodev.comucarona.feature.registeraccount.data.di

import org.app.marcelodev.comucarona.feature.registeraccount.data.RegisterAccountRepository
import org.app.marcelodev.comucarona.feature.registeraccount.data.external.RegisterAccountAPI
import org.app.marcelodev.comucarona.feature.registeraccount.data.external.RegisterAccountAPIImpl
import org.app.marcelodev.comucarona.feature.registeraccount.data.RegisterAccountRepositoryImpl
import org.app.marcelodev.comucarona.feature.registeraccount.domain.RegisterAccountUseCase
import org.app.marcelodev.comucarona.feature.registeraccount.ui.RegisterAccountViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object RegisterAccountModule {
    val module = module {
        factory<RegisterAccountAPI> { RegisterAccountAPIImpl(get()) }
        factory<RegisterAccountRepository> { RegisterAccountRepositoryImpl(get()) }
        factory {
            RegisterAccountUseCase(
                authPreferences = get(),
                registerAccountRepository = get()
            )
        }

        viewModel { param ->
            RegisterAccountViewModel(
                navigator = param.get(),
                snackbarHostState = param.get(),
                registerAccountUseCase = get(),
                logoutUseCase = get(),
            )
        }
    }
}