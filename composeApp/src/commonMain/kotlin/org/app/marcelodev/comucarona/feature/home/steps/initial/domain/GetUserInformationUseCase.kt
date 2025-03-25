package org.app.marcelodev.comucarona.feature.home.steps.initial.domain

import org.app.marcelodev.comucarona.feature.home.steps.initial.data.InitialRepository
import org.app.marcelodev.comucarona.service.ktor.UserInformation

class GetUserInformationUseCase(
    private val initialRepository: InitialRepository
) {
    suspend operator fun invoke(): Result<UserInformation> {
        return initialRepository.getUserInformation()
    }
}