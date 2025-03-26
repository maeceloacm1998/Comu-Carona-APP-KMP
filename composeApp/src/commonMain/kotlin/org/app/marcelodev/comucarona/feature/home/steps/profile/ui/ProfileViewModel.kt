package org.app.marcelodev.comucarona.feature.home.steps.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.navigator.Navigator
import org.app.marcelodev.comucarona.feature.home.steps.profile.ui.ProfileViewModelEventState.OnLoadProfile
import org.app.marcelodev.comucarona.feature.home.steps.profile.ui.ProfileViewModelEventState.OnLogout
import org.app.marcelodev.comucarona.feature.home.steps.profile.ui.ProfileViewModelEventState.OnNavigateToProfileDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.app.marcelodev.comucarona.commons.usecase.LogoutUseCase
import org.app.marcelodev.comucarona.commons.utils.NavigationUtils
import org.app.marcelodev.comucarona.feature.home.steps.profile.domain.GetProfileUseCase
import org.app.marcelodev.comucarona.feature.profiledetails.ProfileDetailsRoute
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountRequest
import org.app.marcelodev.comucarona.service.ktor.extensions.handleHttpException
import org.koin.core.component.KoinComponent

class ProfileViewModel(
    private val navigator: Navigator,
    private val logoutUseCase: LogoutUseCase,
    private val getProfileUseCase: GetProfileUseCase
) : ScreenModel, ViewModel(), KoinComponent {
    private val viewModelState = MutableStateFlow(ProfileViewModelState())

    init {
        onFetchProfile()
    }

    val uiState = viewModelState
        .map(ProfileViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun onEvent(event: ProfileViewModelEventState) {
        when (event) {
            is OnLoadProfile -> onFetchProfile()
            is OnLogout -> handleLogout()
            is OnNavigateToProfileDetails -> onNavigateToProfileDetails()
        }
    }

    private fun onNavigateToProfileDetails() {
        val profileInformation = checkNotNull(viewModelState.value.profileInformation)

        navigator.parent?.let {
            NavigationUtils.addNewScreen(
                navigator = it,
                screen = ProfileDetailsRoute(
                    userName = profileInformation.fullName,
                    birthDate = profileInformation.birthDate,
                    phoneNumber = profileInformation.phoneNumber,
                )
            )
        }
    }

    private fun onFetchProfile() {
        onUpdateLoading(true)

        viewModelScope.launch {
            val result = getProfileUseCase()

            result.fold(
                onSuccess = { profileInformation ->
                    onUpdateLoading(false)
                    onUpdateProfile(profileInformation)
                },
                onFailure = { throwable ->
                    throwable.handleHttpException(
                        onUnauthorized = {
                            handleLogout()
                        },
                        others = {
                            onUpdateLoading(false)
                            onUpdateError(true)
                        }
                    )
                }
            )
        }
    }

    private fun handleLogout() {
        navigator.parent?.let { logoutUseCase(navigator = it) }
    }

    private fun onUpdateProfile(userInformation: RegisterAccountRequest) {
        viewModelState.update { it.copy(profileInformation = userInformation) }
    }

    private fun onUpdateLoading(isLoading: Boolean) {
        viewModelState.update { it.copy(isLoading = isLoading) }
    }

    private fun onUpdateError(isError: Boolean) {
        viewModelState.update { it.copy(isError = isError) }
    }
}