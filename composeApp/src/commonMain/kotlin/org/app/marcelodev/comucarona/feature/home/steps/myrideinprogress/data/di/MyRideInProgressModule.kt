package org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.data.di

import org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.data.MyRideInProgressRepository
import org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.data.MyRideInProgressRepositoryImpl
import org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.data.external.MyRideInProgressAPI
import org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.data.external.MyRideInProgressAPImpl
import org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.domain.GetMyRideInProgressUseCase
import org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.ui.MyRideInProgressViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object MyRideInProgressModule {
    val module = module {
        factory<MyRideInProgressAPI> { MyRideInProgressAPImpl(
            client = get()
        ) }

        factory<MyRideInProgressRepository> { MyRideInProgressRepositoryImpl(
            rideInProgressAPI = get()
        )}

        factory { GetMyRideInProgressUseCase(rideInProgressRepository = get()) }

        viewModel { params ->
            MyRideInProgressViewModel(
                navigator = params.get(),
                getMyRideInProgressUseCase = get(),
                logoutUseCase = get()
            )
        }
    }
}