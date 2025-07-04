package org.app.marcelodev.comucarona.feature.home.steps.profile.domain

import org.app.marcelodev.comucarona.feature.home.steps.profile.data.ProfileRepository
import org.app.marcelodev.comucarona.feature.registeraccount.data.models.RegisterAccountRequest

class UpdateProfileUseCase(
    private val profileRepository: ProfileRepository,
) {
    suspend operator fun invoke(
        userName: String,
        phoneNumber: String
    ): Result<RegisterAccountRequest> {
        val newRegisterUpdate = RegisterAccountRequest(
            fullName = userName,
            phoneNumber = phoneNumber,
            photoUrl = ""
        )

        return profileRepository.updateProfile(newRegisterUpdate)
    }
}