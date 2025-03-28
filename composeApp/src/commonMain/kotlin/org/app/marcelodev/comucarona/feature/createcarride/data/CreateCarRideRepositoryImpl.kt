package org.app.marcelodev.comucarona.feature.createcarride.data

import org.app.marcelodev.comucarona.feature.createcarride.data.external.CreateCarRideAPI
import org.app.marcelodev.comucarona.feature.createcarride.data.models.CreateCarRideRequest
import org.app.marcelodev.comucarona.feature.createcarride.data.models.LastCarRide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.app.marcelodev.comucarona.service.ktor.extensions.handleResultResponse

class CreateCarRideRepositoryImpl(
    private val createCarRideAPI: CreateCarRideAPI
): CreateCarRideRepository {
    override suspend fun searchAddress(address: String): Result<List<String>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = createCarRideAPI.searchAddress(address)
                return@withContext response.handleResultResponse<List<String>>()
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun createCarRide(data: CreateCarRideRequest): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                createCarRideAPI.createCarRide(data)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getLastCarRide(): Result<LastCarRide> {
        return withContext(Dispatchers.IO) {
            try {
                val response = createCarRideAPI.getLastCarRide()
                return@withContext response.handleResultResponse<LastCarRide>()
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}