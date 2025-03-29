package org.app.marcelodev.comucarona.feature.rideinprogressDetails.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.data.external.MyRideInProgressDetailsAPI

class RideInProgressDetailsRepositoryImpl(
    private val myCarRideInProgressDetailsAPI: MyRideInProgressDetailsAPI
): RideInProgressDetailsRepository {
    override suspend fun deleteReservation(riderId: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                myCarRideInProgressDetailsAPI.deleteReservation(riderId)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}