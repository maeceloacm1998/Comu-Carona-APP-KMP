package org.app.marcelodev.comucarona.feature.carridedetails.data

import org.app.marcelodev.comucarona.feature.carridedetails.data.external.CarRideDetailsAPI
import org.app.marcelodev.comucarona.feature.carridedetails.data.models.CarRideDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.app.marcelodev.comucarona.service.ktor.extensions.handleResultResponse

class CarRideDetailsRepositoryImpl(
    private val carRideDetailsAPI: CarRideDetailsAPI
) : CarRideDetailsRepository {
    override suspend fun getCarRideDetails(id: String): Result<CarRideDetails> {
        return withContext(Dispatchers.IO) {
            try {
                val carRideDetails = carRideDetailsAPI.getCarRideDetails(id)
                return@withContext carRideDetails.handleResultResponse<CarRideDetails>()
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun reservationRide(riderId: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                carRideDetailsAPI.reservationRide(riderId)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}