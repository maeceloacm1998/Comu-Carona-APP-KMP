package org.app.marcelodev.comucarona.feature.rideinprogressDetails.data.di

import org.app.marcelodev.comucarona.feature.rideinprogressDetails.data.RideInProgressDetailsRepository
import org.app.marcelodev.comucarona.feature.rideinprogressDetails.data.RideInProgressDetailsRepositoryImpl
import org.app.marcelodev.comucarona.feature.rideinprogressDetails.domain.DeleteReservationsUseCase
import org.app.marcelodev.comucarona.feature.rideinprogressDetails.ui.RideInProgressDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object RideInProgressDetailsModule {
    val module = module {
        factory<RideInProgressDetailsRepository> { RideInProgressDetailsRepositoryImpl(
            myCarRideInProgressDetailsAPI = get()
        ) }

        factory { DeleteReservationsUseCase(repository = get()) }

        viewModel { param ->
            RideInProgressDetailsViewModel(
                riderId = param.get(),
                navigator = param.get(),
                snackbarHostState = param.get(),
                getCarRideDetailsUseCase = get(),
                deleteReservationsUseCase = get(),
                callWhatsappUseCase = get(),
                callPhoneUseCase = get(),
                shareLinkUseCase = get(),
                logoutUseCase = get()
            )
        }
    }
}