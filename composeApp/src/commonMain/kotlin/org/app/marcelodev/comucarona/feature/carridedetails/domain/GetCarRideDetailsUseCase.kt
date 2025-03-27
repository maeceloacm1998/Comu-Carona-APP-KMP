package org.app.marcelodev.comucarona.feature.carridedetails.domain

import org.app.marcelodev.comucarona.feature.carridedetails.data.CarRideDetailsRepository

class GetCarRideDetailsUseCase(
    private val carRideDetailsRepository: CarRideDetailsRepository
) {
    suspend operator fun invoke(id: String) = carRideDetailsRepository.getCarRideDetails(id)
}