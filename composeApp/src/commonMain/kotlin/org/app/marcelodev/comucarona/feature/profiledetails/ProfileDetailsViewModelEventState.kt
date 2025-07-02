package org.app.marcelodev.comucarona.feature.profiledetails

import io.github.vinceglb.filekit.PlatformFile


sealed class ProfileDetailsViewModelEventState {

    /**
     * Represents the event when the back button is clicked.
     */
    data object OnBack : ProfileDetailsViewModelEventState()

    /**
     * Represents the event when the full name is updated.
     *
     * @param fullName The new full name.
     */
    data class OnUpdateFullName(val fullName: String) : ProfileDetailsViewModelEventState()

    /**
     * Represents the event when the phone number is updated.
     *
     * @param phoneNumber The new phone number.
     */
    data class OnUpdatePhoneNumber(val phoneNumber: String) : ProfileDetailsViewModelEventState()

    /**
     * Represents the event when the photo is updated.
     */
    data class OnUpdatePhoto(val uri: PlatformFile?) : ProfileDetailsViewModelEventState()

    /**
     * Represents the event when the photo is updated.
     */
    data object OnUpdateProfile : ProfileDetailsViewModelEventState()
}