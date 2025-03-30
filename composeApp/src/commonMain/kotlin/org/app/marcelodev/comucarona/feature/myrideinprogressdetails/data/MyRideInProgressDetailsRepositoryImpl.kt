package org.app.marcelodev.comucarona.feature.myrideinprogressdetails.data

import org.app.marcelodev.comucarona.feature.myrideinprogressdetails.data.external.MyRideInProgressDetailsAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class MyRideInProgressDetailsRepositoryImpl(
    private val myCarRideInProgressDetailsAPI: MyRideInProgressDetailsAPI
): MyRideInProgressDetailsRepository {
    override suspend fun deleteCarRide(riderId: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                myCarRideInProgressDetailsAPI.deleteCarRide(riderId)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun finishCarRide(riderId: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                myCarRideInProgressDetailsAPI.finishCarRide(riderId)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}