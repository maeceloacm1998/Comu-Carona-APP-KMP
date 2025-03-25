package org.app.marcelodev.comucarona.feature.home.di

import org.app.marcelodev.comucarona.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

object HomeModule {
    val module = module {
        viewModel { HomeViewModel() }
    }
}