package org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.data

import org.app.marcelodev.comucarona.feature.home.steps.myrideinprogress.data.external.MyRideInProgressAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressModel
import org.app.marcelodev.comucarona.service.ktor.extensions.handleResultResponse

class MyRideInProgressRepositoryImpl(
    private val rideInProgressAPI: MyRideInProgressAPI
): MyRideInProgressRepository {
    override suspend fun getMyRideInProgress(
        status: String?
    ): Result<List<RideInProgressModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = rideInProgressAPI.getMyRideInProgress(status = status)
                return@withContext response.handleResultResponse<List<RideInProgressModel>>()
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

}