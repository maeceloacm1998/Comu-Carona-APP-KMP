package org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.ui.MyRideInProgressViewModelEventState.OnLoadMyRideInProgress
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.generic_connection_error
import comucarona.composeapp.generated.resources.ic_car_ride
import org.app.marcelodev.comucarona.components.contenterror.CCErrorContentRetry
import org.app.marcelodev.comucarona.components.contentloading.CCLoadingShimmerContent
import org.app.marcelodev.comucarona.components.contentloading.CCLoadingSwipeRefreshContent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf

object MyRideInProgressRoute : Tab {
    @Composable
    override fun Content() {
        var isFocused by remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<MyRideInProgressViewModel>(
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
            viewModel.onEvent(OnLoadMyRideInProgress)
            isFocused = true
        }

        if(isFocused) {
            MyRideInProgressRoute(
                uiState = uiState,
                onEvent = viewModel::onEvent
            )
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Minhas Caronas"
            val icon = painterResource(Res.drawable.ic_car_ride)

            return remember {
                TabOptions(
                    index = 3u,
                    title = title,
                    icon = icon
                )
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRideInProgressRoute(
    uiState: MyRideInProgressViewModelUiState,
    onEvent: (MyRideInProgressViewModelEventState) -> Unit
) {
    CCLoadingSwipeRefreshContent(
        isLoading = uiState.isLoading,
        isRefresh = uiState.isRefresh,
        isError = uiState.isError,
        onRefresh = { onEvent(OnLoadMyRideInProgress) },
        loadingContent = {
            CCLoadingShimmerContent()
        },
        errorContent = {
            CCErrorContentRetry(
                title = stringResource(Res.string.generic_connection_error),
                onClick = { onEvent(OnLoadMyRideInProgress) }
            )
        },
        content = {
            MyRideInProgressScreen(
                uiState = uiState,
                onEvent = onEvent
            )
        }
    )
}