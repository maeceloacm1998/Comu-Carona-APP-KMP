package org.app.marcelodev.comucarona.feature.myrideinprogressdetails.domain

import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.data.MyRideInProgressDetailsRepository

class DeleteCarRideUseCase(
    private val myRideInProgressDetailsRepository: MyRideInProgressDetailsRepository
) {
    suspend operator fun invoke(riderId: String) = myRideInProgressDetailsRepository.deleteCarRide(riderId)
}