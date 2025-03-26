package org.app.marcelodev.comucarona.feature.home.steps.profile.ui

import org.app.marcelodev.comucarona.feature.home.steps.profile.ui.ProfileViewModelUiState.HasProfile
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountRequest


sealed interface ProfileViewModelUiState{
    val isLoading: Boolean
    val isError: Boolean

    data class HasProfile(
        val profileInformation: RegisterAccountRequest?,
        override val isLoading: Boolean,
        override val isError: Boolean
    ) : ProfileViewModelUiState
}

data class ProfileViewModelState(
    val profileInformation: RegisterAccountRequest? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false
) {
    fun toUiState(): ProfileViewModelUiState = HasProfile(
        profileInformation = profileInformation,
        isLoading = isLoading,
        isError = isError
    )
}
