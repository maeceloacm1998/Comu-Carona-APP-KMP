package org.app.marcelodev.comucarona.feature.profiledetails

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.navigator.Navigator
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.app.marcelodev.comucarona.commons.usecase.LogoutUseCase
import org.app.marcelodev.comucarona.commons.utils.NavigationUtils
import org.app.marcelodev.comucarona.components.snackbar.SnackbarCustomType
import org.app.marcelodev.comucarona.feature.home.steps.initial.domain.GetUserInformationUseCase
import org.app.marcelodev.comucarona.feature.home.steps.profile.domain.UpdateProfileUseCase
import org.app.marcelodev.comucarona.feature.profiledetails.ProfileDetailsViewModelEventState.*
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountRequest
import org.app.marcelodev.comucarona.feature.registeraccount.domain.UploadPhotoUseCase
import org.app.marcelodev.comucarona.service.ktor.extensions.handleHttpException
import org.koin.core.component.KoinComponent

class ProfileDetailsViewModel(
    private val navigator: Navigator,
    private val userName: String,
    private val birthDate: String,
    private val phoneNumber: String,
    private val snackbarHostState: SnackbarHostState,
    private val photoUseCase: UploadPhotoUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getUserInformationUseCase: GetUserInformationUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ScreenModel, ViewModel(), KoinComponent {
    private val viewModelState = MutableStateFlow(ProfileDetailsViewModelState())

    init {
        onUpdateProfileDetails()
        onFetchUserInformation()
    }

    val uiState = viewModelState
        .map(ProfileDetailsViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun onEvent(event: ProfileDetailsViewModelEventState) {
        when (event) {
            is OnBack -> NavigationUtils.removeLastScreen(navigator)
            is OnUpdatePhoto -> onUpdatePhoto(event.uri)
            is OnUpdateFullName -> onUpdateFullName(event.fullName)
            is OnUpdateBirthDate -> onUpdateBirthDate(event.birthDate)
            is OnUpdatePhoneNumber -> onUpdatePhoneNumber(event.phoneNumber)
            is OnUpdateProfile -> onUpdateProfile()
        }
    }

    private fun onUpdatePhoto(uri: PlatformFile?) {
        onUpdateLoadingImage(true)

        viewModelScope.launch {
            uri?.readBytes()?.let {
                photoUseCase(it)
                    .onSuccess {
                        onUpdateLoadingImage(false)
                        onUpdatePhotoUrl(it.uri)
                        onUpdateShowSnackBar(
                            snackbarType = SnackbarCustomType.SUCCESS,
                            snackBarMessage = "Foto atualizada com sucesso! 😎\n" +
                                    "Feche o aplicativo para atualizar a foto."
                        )
                    }
                    .onFailure { throwable ->
                        throwable.handleHttpException(
                            onUnauthorized = {
                                logoutUseCase(navigator = navigator)
                            },
                            others = {
                                onUpdateLoadingImage(false)
                                onUpdateShowSnackBar(
                                    snackbarType = SnackbarCustomType.ERROR,
                                    snackBarMessage = "Erro ao atualizar a foto"
                                )
                            }
                        )
                    }
            }
        }
    }

    private fun onUpdateProfile() {
        onUpdateLoadingUpdate(true)

        viewModelScope.launch {
            val profileInformation = checkNotNull(viewModelState.value.profileDetailsInformation)
            updateProfileUseCase(
                userName = profileInformation.fullName,
                birthDate = profileInformation.birthDate,
                phoneNumber = profileInformation.phoneNumber,
            ).onSuccess {
                onUpdateLoadingUpdate(false)
                onUpdateSuccessUpdate(true)
                onUpdateShowSnackBar(
                    snackbarType = SnackbarCustomType.SUCCESS,
                    snackBarMessage = "Perfil atualizado com sucesso! 😎"
                )
            }.onFailure { throwable ->
                throwable.handleHttpException(
                    onUnauthorized = {
                        logoutUseCase(navigator = navigator)
                    },
                    others = {
                        onUpdateLoadingUpdate(false)
                        onUpdateShowSnackBar(
                            snackbarType = SnackbarCustomType.ERROR,
                            snackBarMessage = "Erro ao atualizar o perfil. Tente novamente."
                        )
                    }
                )
            }
        }
    }

    private fun onFetchUserInformation() {
        onUpdateLoadingImage(true)

        viewModelScope.launch {
            val result = getUserInformationUseCase()

            result.fold(
                onSuccess = { userInformation ->
                    onUpdateLoadingImage(false)
                    onUpdatePhotoUrl(userInformation.photoUrl)
                },
                onFailure = { throwable ->
                    throwable.handleHttpException(
                        onUnauthorized = {
                            logoutUseCase(navigator = navigator)
                        },
                        others = {
                            onUpdateLoadingImage(false)
                            onUpdateShowSnackBar(
                                snackbarType = SnackbarCustomType.ERROR,
                                snackBarMessage = "Erro ao buscar informações do usuário"
                            )
                        }
                    )
                }
            )
        }
    }

    private fun onUpdateProfileDetails() {
        viewModelState.update {
            it.copy(
                profileDetailsInformation = RegisterAccountRequest(
                    fullName = userName,
                    birthDate = birthDate,
                    phoneNumber = phoneNumber,
                    photoUrl = ""
                )
            )
        }
    }

    private fun onUpdateShowSnackBar(
        snackBarMessage: String,
        snackbarType: SnackbarCustomType
    ) {
        viewModelState.update {
            it.copy(snackbarType = snackbarType)
        }

        viewModelScope.launch {
            snackbarHostState.showSnackbar(snackBarMessage)
        }
    }

    private fun onUpdateFullName(fullName: String) {
        viewModelState.update {
            it.copy(
                profileDetailsInformation = it.profileDetailsInformation?.copy(fullName = fullName)
            )
        }
        onUpdateIsChangeFields(true)
    }

    private fun onUpdateBirthDate(birthDate: String) {
        viewModelState.update {
            it.copy(
                profileDetailsInformation = it.profileDetailsInformation?.copy(birthDate = birthDate)
            )
        }
        onUpdateIsChangeFields(true)
    }

    private fun onUpdatePhoneNumber(phoneNumber: String) {
        viewModelState.update {
            it.copy(
                profileDetailsInformation = it.profileDetailsInformation?.copy(phoneNumber = phoneNumber)
            )
        }
        onUpdateIsChangeFields(true)
    }

    private fun onUpdateIsChangeFields(isChangeFields: Boolean) {
        viewModelState.update {
            it.copy(isChangeFields = isChangeFields)
        }
    }

    private fun onUpdatePhotoUrl(photoUrl: String) {
        viewModelState.update {
            it.copy(
                profileDetailsInformation = it.profileDetailsInformation?.copy(photoUrl = photoUrl)
            )
        }
    }

    private fun onUpdateLoadingImage(isLoading: Boolean) {
        viewModelState.update {
            it.copy(isLoadingImage = isLoading)
        }
    }

    private fun onUpdateLoadingUpdate(isLoading: Boolean) {
        viewModelState.update {
            it.copy(isLoadingUpdate = isLoading)
        }
    }

    private fun onUpdateSuccessUpdate(isSuccess: Boolean) {
        viewModelState.update {
            it.copy(isSuccessUpdate = isSuccess)
        }
    }

}