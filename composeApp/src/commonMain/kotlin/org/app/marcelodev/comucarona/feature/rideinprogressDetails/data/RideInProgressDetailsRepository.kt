package org.app.marcelodev.comucarona.feature.rideinprogressDetails.data

interface RideInProgressDetailsRepository {
    suspend fun deleteReservation(riderId: String): Result<Unit>
}