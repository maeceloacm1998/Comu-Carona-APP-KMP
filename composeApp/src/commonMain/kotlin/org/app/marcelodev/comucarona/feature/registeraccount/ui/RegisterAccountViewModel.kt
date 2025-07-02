package org.app.marcelodev.comucarona.feature.registeraccount.ui

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.navigator.Navigator
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.readBytes
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountSteps
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountSteps.PHOTO
import org.app.marcelodev.comucarona.feature.registeraccount.domain.RegisterAccountUseCase
import org.app.marcelodev.comucarona.feature.registeraccount.ui.RegisterAccountViewModelEventState.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.app.marcelodev.comucarona.commons.usecase.LogoutUseCase
import org.app.marcelodev.comucarona.commons.utils.DateUtils.isValidBirthDate
import org.app.marcelodev.comucarona.commons.utils.NavigationUtils
import org.app.marcelodev.comucarona.feature.home.HomeRoutePatern
import org.app.marcelodev.comucarona.feature.registeraccount.domain.UploadPhotoUseCase
import org.app.marcelodev.comucarona.service.ktor.extensions.handleHttpException
import org.koin.core.component.KoinComponent

class RegisterAccountViewModel(
    private val navigator: Navigator,
    private val snackbarHostState: SnackbarHostState,
    private val registerAccountUseCase: RegisterAccountUseCase,
    private val photoUseCase: UploadPhotoUseCase,
    private val logoutUseCase: LogoutUseCase
) : ScreenModel, ViewModel(), KoinComponent {
    private val snackbarMessageError = "Aconteceu um erro ao registrar o usuário, tenta novamente."
    private val viewModelState = MutableStateFlow(RegisterAccountViewModelState())
    private val stepsOrder: List<RegisterAccountSteps> = RegisterAccountSteps.entries.toTypedArray().toList()

    val uiState = viewModelState
        .map(RegisterAccountViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun onEvent(event: RegisterAccountViewModelEventState) {
        when (event) {
            is OnNextStep -> onNextStep(event.step)
            is OnRemoveNewStep -> onRemoveNewStep(event.step)
            is OnUpdateFullName -> onUpdateFullName(event.fullName)
            is OnUpdatePhoneNumber -> onUpdatePhoneNumber(event.phoneNumber)
            is OnUpdatePhoto -> onUpdatePhoto(event.photo)
        }
    }

    private fun onNextStep(step: RegisterAccountSteps) {
        if (step == PHOTO) {
            onRegisterUser()
        } else {
            val index = stepsOrder.indexOf(step)
            onUpdateStep(stepsOrder[index + 1])
        }
    }

    private fun onRemoveNewStep(step: RegisterAccountSteps) {
        val index = stepsOrder.indexOf(step)

        if (index > 0) {
            onUpdateStep(stepsOrder[index - 1])
        }
    }

    private fun onRegisterUser() {
        val state = viewModelState.value
        onUpdateLoading(true)

        viewModelScope.launch {
            registerAccountUseCase(
                fullName = state.fullName,
                phoneNumber = state.phoneNumber,
            ).onSuccess { registerResponse ->
                if(state.photoUrl != null) {
                    onFetchUploadPhoto(state.photoUrl.readBytes())
                } else {
                    photoUseCase(registerResponse.photoUrl)
                    handleStatusSuccessful()
                }
            }.onFailure { throwable ->
                throwable.handleHttpException(
                    onUnauthorized = {
                        logoutUseCase(navigator = navigator)
                    },
                    others = {
                        println("Error ${throwable.message}")
                        onUpdateLoading(false)
                        showSnackbarMessage(snackbarMessageError)
                    }
                )
            }
        }
    }

    private suspend fun onFetchUploadPhoto(photoBityArray: ByteArray) {
        photoUseCase(photoBityArray).onSuccess {
            handleStatusSuccessful()
        }.onFailure { throwable ->
            throwable.handleHttpException(
                onUnauthorized = {
                    logoutUseCase(navigator = navigator)
                },
                others = {
                    println("Error ${throwable.message}")
                    onUpdateLoading(false)
                    showSnackbarMessage(snackbarMessageError)
                }
            )
        }
    }

    private fun handleStatusSuccessful() {
        onGoToHome()
        onUpdateLoading(false)
        onUpdateSuccess(true)
    }

    private fun onGoToHome() {
        NavigationUtils.replaceAllScreens(navigator, HomeRoutePatern())
    }

    private fun onUpdateStep(step: RegisterAccountSteps) {
        viewModelState.update { it.copy(steps = step) }
    }

    private fun onUpdatePhoto(photo: PlatformFile?) {
        viewModelState.update { it.copy(photoUrl = photo) }
    }

    private fun onUpdateFullName(fullName: String) {
        viewModelState.update { it.copy(fullName = fullName) }
    }

    private fun onUpdateBirthDate(birthDate: String) {
        viewModelState.update { currentState ->
            val isValid = if (birthDate.length == 10) {
                isValidBirthDate(birthDate)
            } else {
                false
            }

            currentState.copy(
                birthDateErro = !isValid,
            )
        }
    }

    private fun onUpdatePhoneNumber(phoneNumber: String) {
        viewModelState.update { it.copy(phoneNumber = phoneNumber) }
    }

    private fun onUpdateLoading(isLoading: Boolean) {
        viewModelState.update { it.copy(isLoading = isLoading) }
    }

    private fun onUpdateSuccess(isSuccess: Boolean) {
        viewModelState.update { it.copy(isSuccess = isSuccess) }
    }

    private fun showSnackbarMessage(message: String) {
        viewModelScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }
}