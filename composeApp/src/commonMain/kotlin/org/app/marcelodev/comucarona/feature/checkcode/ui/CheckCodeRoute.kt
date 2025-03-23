package org.app.marcelodev.comucarona.feature.checkcode.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.parameter.parametersOf

class CheckCodeRoute: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val chatViewModel = koinScreenModel<CheckCodeViewModel>(
            parameters = {
                parametersOf(navigator)
            }
        )

        val uiState by chatViewModel.uiState.collectAsStateWithLifecycle()

        CheckCodeRoute(
            uiState = uiState,
            onEvent = chatViewModel::onEvent
        )
    }
}

@Composable
fun CheckCodeRoute(
    uiState: CheckCodeViewModelUiState,
    onEvent: (CheckCodeViewModelEventState) -> Unit
) {
    check(uiState is CheckCodeViewModelUiState.Code)
    CheckCodeScreen(
        uiState = uiState,
        onEvent = onEvent
    )
}