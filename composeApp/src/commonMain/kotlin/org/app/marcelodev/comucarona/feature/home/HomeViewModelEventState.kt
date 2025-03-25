package org.app.marcelodev.comucarona.feature.home

sealed class HomeViewModelEventState {
    data class OnNavigateTo(val route: String): HomeViewModelEventState()
}