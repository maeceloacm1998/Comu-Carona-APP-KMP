package org.app.marcelodev.comucarona.feature.home.steps.profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.app.marcelodev.comucarona.feature.home.steps.profile.ui.ProfileViewModelEventState.OnLoadProfile
import comucarona.composeapp.generated.resources.Res
import comucarona.composeapp.generated.resources.generic_connection_error
import comucarona.composeapp.generated.resources.ic_profile
import org.app.marcelodev.comucarona.components.contenterror.CCErrorContentRetry
import org.app.marcelodev.comucarona.components.contentloading.CCLoadingContent
import org.app.marcelodev.comucarona.components.contentloading.CCLoadingShimmerContent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf

object ProfileRoute : Tab {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val chatViewModel = koinScreenModel<ProfileViewModel>(
            parameters = {
                parametersOf(navigator)
            }
        )

        val uiState by chatViewModel.uiState.collectAsStateWithLifecycle()

        ProfileRoute(
            uiState = uiState,
            onEvent = chatViewModel::onEvent
        )
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = "Perfil"
            val icon = painterResource(Res.drawable.ic_profile)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }
}

@Composable
fun ProfileRoute(
    uiState: ProfileViewModelUiState,
    onEvent: (ProfileViewModelEventState) -> Unit
) {
    CCLoadingContent(
        isLoading = uiState.isLoading,
        isError = uiState.isError,
        loadingContent = {
            CCLoadingShimmerContent()
        },
        errorContent = {
            CCErrorContentRetry(
                title = stringResource(Res.string.generic_connection_error),
                onClick = {
                    onEvent(OnLoadProfile)
                }
            )
        },
        content = {
            check(uiState is ProfileViewModelUiState.HasProfile)

            ProfileScreen(
                uiState = uiState,
                onEvent = onEvent
            )
        }
    )
}