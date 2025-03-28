package org.app.marcelodev.comucarona.feature.createcarride.domain

import org.app.marcelodev.comucarona.feature.createcarride.data.CreateCarRideRepository
import org.app.marcelodev.comucarona.feature.createcarride.data.models.LastCarRide

class GetLastCarRideUseCase(
    private val createCarRideRepository: CreateCarRideRepository
) {
    suspend operator fun invoke(): Result<LastCarRide> = createCarRideRepository.getLastCarRide()
}