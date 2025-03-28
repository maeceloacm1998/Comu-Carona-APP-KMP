package org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data

import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.external.RideInProgressAPI
import org.app.marcelodev.comucarona.feature.home.steps.rideinprogress.data.models.RideInProgressModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.app.marcelodev.comucarona.service.ktor.extensions.handleResultResponse

class RideInProgressRepositoryImpl(
    private val rideInProgressAPI: RideInProgressAPI
): RideInProgressRepository {
    override suspend fun getRideInProgress(
        status: String?
    ): Result<List<RideInProgressModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = rideInProgressAPI.getRideInProgress(status = status)
                return@withContext response.handleResultResponse<List<RideInProgressModel>>()
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}