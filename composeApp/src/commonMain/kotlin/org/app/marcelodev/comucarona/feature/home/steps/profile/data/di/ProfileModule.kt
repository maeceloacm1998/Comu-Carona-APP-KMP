package org.app.marcelodev.comucarona.feature.home.steps.profile.data.di

import org.app.marcelodev.comucarona.feature.profiledetails.ProfileDetailsViewModel
import org.app.marcelodev.comucarona.feature.home.steps.profile.data.ProfileRepository
import org.app.marcelodev.comucarona.feature.home.steps.profile.data.ProfileRepositoryImpl
import org.app.marcelodev.comucarona.feature.home.steps.profile.data.external.ProfileAPI
import org.app.marcelodev.comucarona.feature.home.steps.profile.data.external.ProfileAPIImpl
import org.app.marcelodev.comucarona.feature.home.steps.profile.domain.GetProfileUseCase
import org.app.marcelodev.comucarona.feature.home.steps.profile.domain.UpdateProfileUseCase
import org.app.marcelodev.comucarona.feature.home.steps.profile.ui.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object ProfileModule {
    val module = module {
        factory<ProfileAPI> {
            ProfileAPIImpl(
                client = get()
            )
        }

        factory<ProfileRepository> {
            ProfileRepositoryImpl(
                profileAPI = get()
            )
        }

        factory { GetProfileUseCase(profileRepository = get()) }
        factory { UpdateProfileUseCase(profileRepository = get()) }

        viewModel { param ->
            ProfileViewModel(
                getProfileUseCase = get(),
                logoutUseCase = get(),
                navigator = param.get()
            )
        }

        viewModel { param ->
            ProfileDetailsViewModel(
                userName = param.get(),
                birthDate = param.get(),
                phoneNumber = param.get(),
                navigator = param.get(),
                snackbarHostState = param.get(),
                getUserInformationUseCase = get(),
                logoutUseCase = get(),
                photoUseCase = get(),
                updateProfileUseCase = get()
            )
        }
    }
}