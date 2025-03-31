package org.app.marcelodev.comucarona.feature.carridedetails.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import comucarona.composeapp.generated.resources.*
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.car_ride_details_complete_button_title
import comucarona.composeapp.generated.resources.car_ride_details_complete_description
import comucarona.composeapp.generated.resources.car_ride_details_complete_title
import org.app.marcelodev.comucarona.commons.utils.AnimatedUtils.animatedTransitionPage
import org.app.marcelodev.comucarona.components.contenterror.CCErrorContentRetry
import org.app.marcelodev.comucarona.components.contentloading.CCLoadingContent
import org.app.marcelodev.comucarona.components.contentloading.CCLoadingShimmerContent
import org.app.marcelodev.comucarona.components.contentsuccess.CCSuccessContent
import org.app.marcelodev.comucarona.feature.carridedetails.ui.CarRideDetailsViewModelEventState.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

class CarRideDetailsRoute(
    private val riderId: String
) : Screen {
    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val snackbarHostState = remember { SnackbarHostState() }
        val navigator: Navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<CarRideDetailsViewModel>(
            parameters = { parametersOf(navigator, snackbarHostState, riderId) }
        )

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LifecycleEffectOnce {
            viewModel.onFetchCarRideDetails(riderId)

            onDispose {
                viewModel.clearState()
                snackbarHostState.currentSnackbarData?.dismiss()
            }
        }

        CarRideDetailsRoute(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            snackbarHostState = snackbarHostState
        )
    }
}

@Composable
fun CarRideDetailsRoute(
    uiState: CarRideDetailsViewModelUiState,
    onEvent: (CarRideDetailsViewModelEventState) -> Unit,
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
                    title = stringResource(Res.string.car_ride_details_complete_title),
                    description = stringResource(Res.string.car_ride_details_complete_description),
                    buttonText = stringResource(Res.string.car_ride_details_complete_button_title),
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
                        check(uiState is CarRideDetailsViewModelUiState.HasCarRideDetails)
                        CarRideDetailsScreen(
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