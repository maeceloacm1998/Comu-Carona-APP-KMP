package org.app.marcelodev.comucarona.feature.createcarride.data.di

import org.app.marcelodev.comucarona.feature.createcarride.data.external.CreateCarRideAPI
import org.app.marcelodev.comucarona.feature.createcarride.data.external.CreateCarRideAPIImpl
import org.app.marcelodev.comucarona.feature.createcarride.data.CreateCarRideRepository
import org.app.marcelodev.comucarona.feature.createcarride.data.CreateCarRideRepositoryImpl
import org.app.marcelodev.comucarona.feature.createcarride.domain.CreateCarRideUseCase
import org.app.marcelodev.comucarona.feature.createcarride.domain.GetLastCarRideUseCase
import org.app.marcelodev.comucarona.feature.createcarride.domain.SearchAddressUseCase
import org.app.marcelodev.comucarona.feature.createcarride.ui.CreateCarRideViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object CreateCarRideModule {
    val module = module {
        factory<CreateCarRideAPI> {
            CreateCarRideAPIImpl(
                client = get()
            )
        }

        factory<CreateCarRideRepository> {
            CreateCarRideRepositoryImpl(
                createCarRideAPI = get()
            )
        }

        factory { CreateCarRideUseCase(createCarRideRepository = get()) }
        factory { GetLastCarRideUseCase(createCarRideRepository = get()) }
        factory { SearchAddressUseCase(createCarRideRepository = get()) }

        viewModel { param ->
            CreateCarRideViewModel(
                navigator = get(),
                createCarRideUseCase = get(),
                getLastCarRideUseCase = get(),
                searchAddressUseCase = get()
            )
        }
    }
}