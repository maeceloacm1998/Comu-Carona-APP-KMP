package org.app.marcelodev.comucarona.feature.rideinprogressDetails.domain

import org.app.marcelodev.comucarona.feature.rideinprogressDetails.data.RideInProgressDetailsRepository

class DeleteReservationsUseCase(
    private val repository: RideInProgressDetailsRepository
) {
    suspend operator fun invoke(riderId: String) = repository.deleteReservation(riderId)
}