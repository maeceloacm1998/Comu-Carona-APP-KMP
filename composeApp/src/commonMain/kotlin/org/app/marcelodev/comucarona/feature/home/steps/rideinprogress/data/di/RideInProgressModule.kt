package org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.di

import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.RideInProgressRepository
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.RideInProgressRepositoryImpl
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.external.RideInProgressAPI
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.external.RideInProgressAPImpl
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.domain.GetRideInProgressUseCase
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.ui.RideInProgressViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object RideInPrgressModule {
    val module = module {
        factory<RideInProgressAPI> { RideInProgressAPImpl(client = get())  }
        factory<RideInProgressRepository> { RideInProgressRepositoryImpl(rideInProgressAPI = get())  }
        factory { GetRideInProgressUseCase(rideInProgressRepository = get()) }

        viewModel { param ->
            RideInProgressViewModel(
                navigator = param.get(),
                getRideInProgressUseCase = get(),
                logoutUseCase = get()
            )
        }
    }
}