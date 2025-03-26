package org.app.marcelodev.comucarona.feature.home.steps.profile.domain

import org.app.marcelodev.comucarona.feature.home.steps.profile.data.ProfileRepository

class GetProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke() = profileRepository.getProfile()
}