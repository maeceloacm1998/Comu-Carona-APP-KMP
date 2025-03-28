package org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.domain

import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.RideInProgressRepository
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressModel

class GetRideInProgressUseCase(
    private val rideInProgressRepository: RideInProgressRepository
) {
    suspend operator fun invoke(status: String = ""): Result<List<RideInProgressModel>> =
        rideInProgressRepository.getRideInProgress(status)

}