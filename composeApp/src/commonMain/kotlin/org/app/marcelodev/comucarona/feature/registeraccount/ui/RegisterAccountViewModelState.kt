package org.app.marcelodev.comucarona.feature.registeraccount.ui

import io.github.vinceglb.filekit.PlatformFile
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountSteps

/**
 * Represents the UI state of the register account screen.
 */
sealed interface RegisterAccountViewModelUiState {

    /**
     * Represents the state when the register account screen is loading.
     */
    data class Register(
        val steps: RegisterAccountSteps,
        val fullName: String,
        val birthDateErro: Boolean,
        val phoneNumber: String,
        val photoUrl: PlatformFile?,
        val isLoading: Boolean,
        val isError: Boolean,
        val isSuccess: Boolean,
    ) : RegisterAccountViewModelUiState
}

/**
 * Represents the state of the register account screen.
 */
data class RegisterAccountViewModelState(
    val steps: RegisterAccountSteps = RegisterAccountSteps.FULL_NAME,
    val fullName: String = "",
    val birthDateErro: Boolean = false,
    val phoneNumber: String = "",
    val photoUrl: PlatformFile? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
) {

    /**
     * Converts the state to a UI state.
     */
    fun toUiState(): RegisterAccountViewModelUiState = RegisterAccountViewModelUiState.Register(
        steps = steps,
        fullName = fullName,
        birthDateErro = birthDateErro,
        phoneNumber = phoneNumber,
        photoUrl = photoUrl,
        isLoading = isLoading,
        isError = isError,
        isSuccess = isSuccess,
    )
}