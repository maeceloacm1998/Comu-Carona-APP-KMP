package org.app.marcelodev.comucarona.feature.home.steps.initial.data.di

import org.app.marcelodev.comucarona.feature.home.steps.initial.data.InitialRepository
import org.app.marcelodev.comucarona.feature.home.steps.initial.data.InitialRepositoryImpl
import org.app.marcelodev.comucarona.feature.home.steps.initial.data.external.InitialAPI
import org.app.marcelodev.comucarona.feature.home.steps.initial.data.external.InitialAPIImpl
import org.app.marcelodev.comucarona.feature.home.steps.initial.domain.AvailableCarRidesUseCase
import org.app.marcelodev.comucarona.feature.home.steps.initial.domain.GetUserInformationUseCase
import org.app.marcelodev.comucarona.feature.home.steps.initial.ui.InitialViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object InitialMdules {
    val module = module {
        factory<InitialAPI> {
            InitialAPIImpl(
                client = get()
            )
        }

        factory<InitialRepository> {
            InitialRepositoryImpl(
                initialAPI = get(),
                authPreferences = get()
            )
        }

        factory {
            AvailableCarRidesUseCase(
                initialRepository = get()
            )
        }

        factory {
            GetUserInformationUseCase(
                initialRepository = get()
            )
        }

        viewModel { param ->
            InitialViewModel(
                availableCarRidesUseCase = get(),
                getUserInformationUseCase = get(),
                logoutUseCase = get(),
                navigator = param.get()
            )
        }
    }
}