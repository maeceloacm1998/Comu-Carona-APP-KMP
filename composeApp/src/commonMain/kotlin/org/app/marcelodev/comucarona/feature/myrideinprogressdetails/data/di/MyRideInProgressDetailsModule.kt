package org.app.marcelodev.comucarona.feature.myrideinprogressdetails.data.di

import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.data.MyRideInProgressDetailsRepository
import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.data.MyRideInProgressDetailsRepositoryImpl
import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.data.external.MyRideInProgressDetailsAPI
import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.data.external.MyRideInProgressDetailsAPIImpl
import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.domain.DeleteCarRideUseCase
import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.ui.MyRideInProgressDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object MyRideInProgressDetailsModule {
    val module = module {
        factory<MyRideInProgressDetailsAPI> { MyRideInProgressDetailsAPIImpl(client = get()) }

        factory<MyRideInProgressDetailsRepository> {
            MyRideInProgressDetailsRepositoryImpl(
                myCarRideInProgressDetailsAPI = get()
            )
        }

        factory { DeleteCarRideUseCase(myRideInProgressDetailsRepository = get()) }

        viewModel { param ->
            MyRideInProgressDetailsViewModel(
                riderId = param.get(),
                navigator = param.get(),
                snackbarHostState = param.get(),
                getCarRideDetailsUseCase = get(),
                deleteCarRideUseCase = get(),
                callWhatsappUseCase = get(),
                callPhoneUseCase = get(),
                shareLinkUseCase = get(),
                logoutUseCase = get()
            )
        }
    }
}