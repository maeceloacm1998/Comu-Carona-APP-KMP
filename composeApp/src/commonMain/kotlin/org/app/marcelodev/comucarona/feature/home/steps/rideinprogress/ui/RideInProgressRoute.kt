package org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.LifecycleEffectOnce
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.ui.RideInProgressViewModelEventState.OnLoadRideInProgress
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.generic_connection_error
import comucarona.composeapp.generated.resources.ic_profile
import org.app.marcelodev.comucarona.components.contenterror.CCErrorContentRetry
import org.app.marcelodev.comucarona.components.contentloading.CCLoadingShimmerContent
import org.app.marcelodev.comucarona.components.contentloading.CCLoadingSwipeRefreshContent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf
import cafe.adriel.voyager.koin.koinScreenModel
import comucarona.composeapp.generated.resources.ic_my_car_ride
import org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.ui.MyRideInProgressViewModelEventState.OnLoadMyRideInProgress
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.ui.RideInProgressViewModelEventState.OnUpdateRideInProgress

object RideInProgressRoute : Tab {
    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        var isFocused by remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<RideInProgressViewModel>(
            parameters = {
                parametersOf(navigator)
            }
        )

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        DisposableEffect(Unit) {
            onDispose {
                isFocused = false
            }
        }

        LaunchedEffect(Unit) {
            viewModel.onEvent(OnLoadRideInProgress)
            isFocused = true
        }

        if (isFocused) {
            RideInProgressRoute(
                uiState = uiState,
                onEvent = viewModel::onEvent
            )
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Minhas Reservas"
            val icon = painterResource(Res.drawable.ic_my_car_ride)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideInProgressRoute(
    uiState: RideInProgressViewModelUiState,
    onEvent: (RideInProgressViewModelEventState) -> Unit
) {
    CCLoadingSwipeRefreshContent(
        isLoading = uiState.isLoading,
        isRefresh = uiState.isRefresh,
        isError = uiState.isError,
        onRefresh = { onEvent(OnUpdateRideInProgress) },
        loadingContent = {
            CCLoadingShimmerContent()
        },
        errorContent = {
            CCErrorContentRetry(
                title = stringResource(Res.string.generic_connection_error),
                onClick = { onEvent(OnLoadRideInProgress) }
            )
        },
        content = {
            RideInProgressScreen(
                uiState = uiState,
                onEvent = onEvent
            )
        }
    )
}