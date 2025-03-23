package org.app.marcelodev.comucarona.feature.checkcode.data.di

import org.app.marcelodev.comucarona.feature.checkcode.data.CheckCodeRepositoryImpl
import org.app.marcelodev.comucarona.feature.checkcode.data.CheckCodeRepository
import org.app.marcelodev.comucarona.feature.checkcode.data.external.CheckCodeAPI
import org.app.marcelodev.comucarona.feature.checkcode.data.external.CheckCodeAPIImpl
import org.app.marcelodev.comucarona.feature.checkcode.domain.CheckCodeUseCase
import org.app.marcelodev.comucarona.feature.checkcode.ui.CheckCodeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object CheckCodeModule {
    val module = module {
        single<CheckCodeAPI> { CheckCodeAPIImpl(client = get()) }
        single<CheckCodeRepository> { CheckCodeRepositoryImpl(checkCodeAPI = get()) }
        single { CheckCodeUseCase(repository = get(), authPreferences = get()) }
        viewModel { params -> CheckCodeViewModel(navigator = params.get(), checkCodeUseCase = get()) }
    }
}