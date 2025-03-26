package org.app.marcelodev.comucarona.feature.profiledetails

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.app.marcelodev.comucarona.feature.profiledetails.ProfileDetailsViewModelUiState.HasProfileDetails
import org.koin.core.parameter.parametersOf

class ProfileDetailsRoute(
    val userName: String,
    val birthDate: String,
    val phoneNumber: String
) : Screen {
    @Composable
    override fun Content() {
        val snackbarHostState = remember { SnackbarHostState() }
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ProfileDetailsViewModel>(
            parameters = {
                parametersOf(navigator, snackbarHostState, userName, birthDate, phoneNumber)
            }
        )

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        ProfileDetailsRoute(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            snackbarHostState = snackbarHostState
        )
    }
}

@Composable
fun ProfileDetailsRoute(
    uiState: ProfileDetailsViewModelUiState,
    onEvent: (ProfileDetailsViewModelEventState) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    check(uiState is HasProfileDetails)

    ProfileDetailsScreen(
        uiState = uiState,
        onEvent = onEvent,
        snackbarHostState = snackbarHostState
    )
}