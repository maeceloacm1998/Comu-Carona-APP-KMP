package org.app.marcelodev.comucarona.feature.home.steps.initial.domain

import org.app.marcelodev.comucarona.feature.home.steps.initial.data.InitialRepository

class AvailableCarRidesUseCase(
    private val initialRepository: InitialRepository
) {
    suspend operator fun invoke() = initialRepository.getAvailableCarRides()
}