package org.app.marcelodev.comucarona.feature.myrideinprogressdetails.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.ui.MyRideInProgressDetailsViewModelEventState.OnGoToHome
import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.ui.MyRideInProgressDetailsViewModelEventState.OnRetry
import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.ui.MyRideInProgressDetailsViewModelUiState.HasCarRideDetails
import comucarona.composeapp.generated.resources.*
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.my_ride_in_progress_details_complete_button_title
import comucarona.composeapp.generated.resources.my_ride_in_progress_details_complete_description
import comucarona.composeapp.generated.resources.my_ride_in_progress_details_complete_title
import org.app.marcelodev.comucarona.commons.utils.AnimatedUtils.animatedTransitionPage
import org.app.marcelodev.comucarona.components.contenterror.CCErrorContentRetry
import org.app.marcelodev.comucarona.components.contentloading.CCLoadingContent
import org.app.marcelodev.comucarona.components.contentloading.CCLoadingShimmerContent
import org.app.marcelodev.comucarona.components.contentsuccess.CCSuccessContent
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf

class MyRideInProgressDetailsRoute(
    private val riderId: String
) : Screen {
    @Composable
    override fun Content() {
        val snackbarHostState = remember { SnackbarHostState() }
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<MyRideInProgressDetailsViewModel>(
            parameters = {
                parametersOf(riderId, navigator, snackbarHostState)
            }
        )

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        CarRideDetailsRoute(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            snackbarHostState = snackbarHostState
        )
    }
}

@Composable
fun CarRideDetailsRoute(
    uiState: MyRideInProgressDetailsViewModelUiState,
    onEvent: (MyRideInProgressDetailsViewModelEventState) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    AnimatedContent(
        targetState = uiState.isSuccessReservation,
        label = "AnimatedContent",
        transitionSpec = animatedTransitionPage()
    ) { targetState ->
        when (targetState) {
            true -> {
                CCSuccessContent(
                    title = stringResource(Res.string.my_ride_in_progress_details_complete_title),
                    description = stringResource(Res.string.my_ride_in_progress_details_complete_description),
                    buttonText = stringResource(Res.string.my_ride_in_progress_details_complete_button_title),
                    onClick = { onEvent(OnGoToHome) }
                )
            }

            else -> {
                CCLoadingContent(
                    isLoading = uiState.isLoading,
                    isError = uiState.isError,
                    loadingContent = {
                        CCLoadingShimmerContent()
                    },
                    errorContent = {
                        CCErrorContentRetry(
                            title = stringResource(Res.string.generic_connection_error),
                            onClick = { onEvent(OnRetry) }
                        )
                    },
                    content = {
                        check(uiState is HasCarRideDetails)
                        MyRideInProgressDetailsScreen(
                            uiState = uiState,
                            onEvent = onEvent,
                            snackbarHostState = snackbarHostState
                        )
                    }
                )
            }
        }
    }
}