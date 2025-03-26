package org.app.marcelodev.comucarona.feature.profiledetails

import org.app.marcelodev.comucarona.components.snackbar.SnackbarCustomType
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountRequest

sealed interface ProfileDetailsViewModelUiState{
    val isLoadingImage: Boolean
    val isLoadingUpdate: Boolean
    val isSuccessUpdate: Boolean
    val snackbarType: SnackbarCustomType

    data class HasProfileDetails(
        val profileDetailsInformation: RegisterAccountRequest?,
        val isChangeFields: Boolean,
        override val snackbarType: SnackbarCustomType,
        override val isLoadingImage: Boolean,
        override val isLoadingUpdate: Boolean,
        override val isSuccessUpdate: Boolean
    ) : ProfileDetailsViewModelUiState
}

data class ProfileDetailsViewModelState(
    val profileDetailsInformation: RegisterAccountRequest? = null,
    val snackbarType: SnackbarCustomType = SnackbarCustomType.SUCCESS,
    val isChangeFields: Boolean = false,
    val isLoadingImage: Boolean = false,
    val isLoadingUpdate: Boolean = false,
    val isSuccessUpdate: Boolean = false
) {
    fun toUiState(): ProfileDetailsViewModelUiState =
        ProfileDetailsViewModelUiState.HasProfileDetails(
            profileDetailsInformation = profileDetailsInformation,
            snackbarType = snackbarType,
            isChangeFields = isChangeFields,
            isLoadingImage = isLoadingImage,
            isLoadingUpdate = isLoadingUpdate,
            isSuccessUpdate = isSuccessUpdate
        )
}