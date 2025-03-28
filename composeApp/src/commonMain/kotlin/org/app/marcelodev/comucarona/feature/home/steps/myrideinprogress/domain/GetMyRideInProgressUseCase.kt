package org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.domain

import org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.data.MyRideInProgressRepository
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressModel

class GetMyRideInProgressUseCase(
    private val rideInProgressRepository: MyRideInProgressRepository
) {
    suspend operator fun invoke(status: String = ""): Result<List<RideInProgressModel>> =
        rideInProgressRepository.getMyRideInProgress(status)

}