package org.app.marcelodev.comucarona.feature.carridedetails.data.di

import org.app.marcelodev.comucarona.feature.carridedetails.data.CarRideDetailsRepository
import org.app.marcelodev.comucarona.feature.carridedetails.data.CarRideDetailsRepositoryImpl
import org.app.marcelodev.comucarona.feature.carridedetails.data.external.CarRideDetailsAPI
import org.app.marcelodev.comucarona.feature.carridedetails.data.external.CarRideDetailsAPIImpl
import org.app.marcelodev.comucarona.feature.carridedetails.domain.GetCarRideDetailsUseCase
import org.app.marcelodev.comucarona.feature.carridedetails.domain.ReservationRideUseCase
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object CarRideDetailsModule {
    val module = module {
        factory<CarRideDetailsAPI> {
            CarRideDetailsAPIImpl(
                client = get()
            )
        }

        factory<CarRideDetailsRepository> {
            CarRideDetailsRepositoryImpl(
                carRideDetailsAPI = get()
            )
        }

        factory {
            GetCarRideDetailsUseCase(
                carRideDetailsRepository = get()
            )
        }

        factory {
            ReservationRideUseCase(
                carRideDetailsRepository = get()
            )
        }

        viewModel { params ->
            CarRideDetailsViewModel(
                navigator = params.get(),
                snackbarHostState = params.get(),
                riderId = params.get(),
                getCarRideDetails = get(),
                reservationRideUseCase = get(),
                callPhoneUseCase = get(),
                callWhatsappUseCase = get(),
                shareLinkUseCase = get(),
                logoutUseCase = get()
            )
        }
    }
}