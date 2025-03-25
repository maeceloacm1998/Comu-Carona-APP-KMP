package org.app.marcelodev.comucarona.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import org.app.marcelodev.comucarona.feature.home.HomeViewModelEventState.OnNavigateTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent

class HomeViewModel : ScreenModel, ViewModel(), KoinComponent {
    private val viewModelState = MutableStateFlow(HomeViewModelState())

    val uiState = viewModelState
        .map(HomeViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun onEvent(event: HomeViewModelEventState) {
        when (event) {
            is OnNavigateTo -> onNavigateTo(event.route)
        }
    }

    private fun onNavigateTo(route: String) {
        viewModelState.update { it.copy(steps = route) }
    }
}