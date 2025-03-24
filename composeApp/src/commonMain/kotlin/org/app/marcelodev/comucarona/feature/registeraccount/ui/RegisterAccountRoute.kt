package org.app.marcelodev.comucarona.feature.registeraccount.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import org.app.marcelodev.comucarona.commons.utils.AnimatedUtils.animatedTransitionPage
import org.app.marcelodev.comucarona.components.snackbar.CCSnackbar
import org.app.marcelodev.comucarona.components.snackbar.SnackbarCustomType
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountSteps.BIRTH_DATE
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountSteps.FULL_NAME
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountSteps.PHONE_NUMBER
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountSteps.PHOTO
import org.koin.core.parameter.parametersOf

class RegisterAccountRoute: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val snackbarHostState = remember { SnackbarHostState() }
        val registerAccountViewModel = koinScreenModel<RegisterAccountViewModel>(
            parameters = {
                parametersOf(navigator, snackbarHostState)
            }
        )

        val uiState by registerAccountViewModel.uiState.collectAsStateWithLifecycle()

        RegisterAccountRoute(
            uiState = uiState,
            event = registerAccountViewModel::onEvent,
            snackbarHostState = snackbarHostState
        )
    }
}

@Composable
    fun RegisterAccountRoute(
    uiState: RegisterAccountViewModelUiState,
    event: (RegisterAccountViewModelEventState) -> Unit,
    snackbarHostState: SnackbarHostState
    ) {
        check(uiState is RegisterAccountViewModelUiState.Register)

        Scaffold(
            snackbarHost = {
                CCSnackbar(
                    snackbarHostState = snackbarHostState,
                    snackbarType = SnackbarCustomType.ERROR
                )
            }
        ) { paddingValues ->
            AnimatedContent(
                modifier = Modifier.padding(paddingValues),
                targetState = uiState.steps,
                label = "AnimatedContent",
                transitionSpec = animatedTransitionPage()
            ) { targetState ->
                when (targetState) {
                    FULL_NAME -> StageOfFullNameScreen(
                        uiState = uiState,
                        event = event
                    )

                    BIRTH_DATE -> StageOfBirthDateScreen(
                        uiState = uiState,
                        event = event
                    )

                    PHONE_NUMBER -> StageOfPhoneNumberScreen(
                        uiState = uiState,
                        event = event
                    )

                    PHOTO -> StageOfPhotoScreen(
                        uiState = uiState,
                        event = event
                    )
                }
            }
        }
    }