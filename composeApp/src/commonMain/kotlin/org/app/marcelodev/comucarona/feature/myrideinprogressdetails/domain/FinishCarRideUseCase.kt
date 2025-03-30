package org.app.marcelodev.comucarona.feature.myrideinprogressdetails.domain

import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.data.MyRideInProgressDetailsRepository

class FinishCarRideUseCase(
    private val myRideInProgressDetailsRepository: MyRideInProgressDetailsRepository
) {
    suspend operator fun invoke(riderId: String) = myRideInProgressDetailsRepository.finishCarRide(riderId)
}