package org.app.marcelodev.comucarona.feature.carridedetails.domain

import org.app.marcelodev.comucarona.feature.carridedetails.data.CarRideDetailsRepository

class ReservationRideUseCase(
    private val carRideDetailsRepository: CarRideDetailsRepository
) {
    suspend operator fun invoke(riderId: String) = carRideDetailsRepository.reservationRide(riderId)
}