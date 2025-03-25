package org.app.marcelodev.comucarona.feature.home.steps.initial.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.ic_home
import org.app.marcelodev.comucarona.components.contentloading.CCLoadingSwipeRefreshContent
import org.jetbrains.compose.resources.painterResource
import org.koin.core.parameter.parametersOf

object InitialRoute : Tab {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val chatViewModel = koinScreenModel<InitialViewModel>(
            parameters = {
                parametersOf(navigator)
            }
        )

        val uiState by chatViewModel.uiState.collectAsStateWithLifecycle()

        InitialRoute(
            uiState = uiState,
            onEvent = chatViewModel::onEvent
        )
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = painterResource(Res.drawable.ic_home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitialRoute(
    uiState: InitialViewModelUiState,
    onEvent: (InitialViewModelEventState) -> Unit
) {
    check(uiState is InitialViewModelUiState.HasAvailableCarRide)

    CCLoadingSwipeRefreshContent(
        isLoading = uiState.isLoading,
        isRefresh = uiState.isRefresh,
        isError = uiState.isError,
        onRefresh = { onEvent(InitialViewModelEventState.OnLoadAvailableCarRide) },
        loadingContent = { },
        errorContent = { },
        content = {
            InitialScreen(
                uiState = uiState,
                onEvent = onEvent
            )
        }
    )
}